package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.BaseEntity;
import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.common.util.validation.BeanValidator;
import edu.noia.myoffice.invoicing.domain.event.debt.*;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import edu.noia.myoffice.invoicing.domain.vo.Recall;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;

import static edu.noia.myoffice.common.util.validation.Rule.condition;
import static edu.noia.myoffice.invoicing.domain.vo.DebtStatus.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Debt extends BaseEntity<Debt, DebtId, DebtState> {

    protected Debt(DebtState state) {
        super(DebtId.random(), state instanceof DebtSample ? (DebtSample) state : DebtSample.from(state));
    }

    private static <T> T validateBean(T state) {
        return BeanValidator.validate(state);
    }

    public static Debt create(DebtState state, Consumer<EventPayload> eventPublisher) {
        return create(state, eventPublisher, Debt::new);
    }

    protected static Debt create(DebtState state, Consumer<EventPayload> eventPublisher, Function<DebtState, Debt> factory) {
        return state.getCartId() != null ?
                invoice(state, eventPublisher, factory) :
                request(state, eventPublisher, factory);
    }

    private static Debt invoice(DebtState state, Consumer<EventPayload> eventPublisher, Function<DebtState, Debt> factory) {
        DebtSample sample = DebtSample.from(validateBean(state))
                .setStatus(CREATED)
                .setAmount(state.getCartAmount().iplus(
                        state.getDiscountRate().iapply(
                                state.getCartAmount().iplus(
                                        state.getTaxRate().iapply(state.getCartAmount())))));

        Debt debt = factory.apply(sample);
        eventPublisher.accept(InvoiceCreatedEventPayload.of(debt.getId(), sample));
        return debt;
    }

    private static Debt request(DebtState state, Consumer<EventPayload> eventPublisher, Function<DebtState, Debt> factory) {
        DebtSample sample = DebtSample.from(validateBean(state)).setStatus(CREATED);
        Debt debt = factory.apply(sample);
        eventPublisher.accept(RequestCreatedEventPayload.of(debt.getId(), sample));
        return debt;
    }

    public DebtType getType() {
        return state.getCartId() != null ? DebtType.INVOICE : DebtType.REQUEST;
    }

    @Override
    public DebtState validate(DebtState state) {
        return validateBean(state);
    }

    @Override
    protected DebtState cloneState() {
        return DebtSample.from(state);
    }

    public Amount getTotal() {
        return state.getAmount();
    }

    public Amount getDebt() {
        return getTotal().iminus(state.getPayedAmount());
    }

    public Debt validate(Consumer<EventPayload> eventPublisher) {
        condition(() -> CREATED == state.getStatus(), String.format("Debt %s has been already validated", getId()));
        state.setStatus(VALIDATED);
        eventPublisher.accept(DebtValidatedEventPayload.of(getId(), DebtSample.from(state)));
        return this;
    }

    public Debt validate(DebtState modifier, Consumer<EventPayload> eventPublisher) {
        condition(() -> CREATED == state.getStatus(), String.format("Debt %s has been already validated", getId()));
        this.patch(modifier);
        state.setStatus(VALIDATED);
        eventPublisher.accept(DebtValidatedEventPayload.of(getId(), DebtSample.from(state)));
        return this;
    }

    public Amount pay(Payment payment, Consumer<EventPayload> eventPublisher) {
        condition(() -> VALIDATED == state.getStatus(), String.format("Debt %s is not validated or already closed", getId()));
        Amount debt = getDebt();
        if (debt.se(payment.getAmount())) {
            eventPublisher.accept(PaymentDoneEventPayload.of(getId(), Payment.of(debt, payment.getDate(), payment.getTicket())));
            eventPublisher.accept(DebtClosedEventPayload.of(getId()));
            return payment.getAmount().iminus(debt);
        } else {
            eventPublisher.accept(PaymentDoneEventPayload.of(getId(), payment));
            return Amount.ZERO;
        }
    }

    public Debt recall(Consumer<EventPayload> eventPublisher) {
        condition(() -> VALIDATED == state.getStatus(), String.format("Debt %s is not validated or already closed", getId()));
        eventPublisher.accept(RecallEmittedEventPayload.of(getId(), Recall.of(getDebt(), LocalDate.now())));
        return this;
    }

    public Holder<Debt> save(DebtRepository repository) {
        return repository.save(id, state);
    }

    protected void created(InvoiceCreatedEventPayload event, Instant timestamp) {
        id = event.getDebtId();
        state = DebtSample.from(event.getDebtSample());
        andEvent(event, timestamp);
    }

    protected void validated(DebtValidatedEventPayload event, Instant timestamp) {
        state.modify(event.getDebtState());
        andEvent(event, timestamp);
    }

    protected void payed(PaymentDoneEventPayload event, Instant timestamp) {
        state.pay(event.getPayment());
        andEvent(event, timestamp);
    }

    protected void recalled(RecallEmittedEventPayload event, Instant timestamp) {
        state.addRecall(event.getRecall());
        andEvent(event, timestamp);
    }

    protected void closed(DebtClosedEventPayload event, Instant timestamp) {
        state.setStatus(CLOSED);
        andEvent(event, timestamp);
    }

    public enum DebtType {
        INVOICE, REQUEST
    }
}
