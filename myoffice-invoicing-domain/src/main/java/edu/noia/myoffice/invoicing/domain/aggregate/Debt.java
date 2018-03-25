package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.BaseEntity;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.common.util.validation.BeanValidator;
import edu.noia.myoffice.invoicing.domain.event.debt.*;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import edu.noia.myoffice.invoicing.domain.vo.DebtStatus;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

import static edu.noia.myoffice.common.util.validation.Rule.condition;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Debt extends BaseEntity<Debt, DebtId, DebtState> {

    protected Debt(DebtState state) {
        super(DebtId.random(), state instanceof DebtSample ? (DebtSample) state : DebtSample.from(state));
    }

    protected static <T> T validateBean(T state) {
        return BeanValidator.validate(state);
    }

    public static Debt create(DebtState state, EventPublisher eventPublisher) {
        DebtSample sample = DebtSample.from(validateBean(state));
        Debt debt = new Debt(sample);
        if (state.getCartId() != null) {
            eventPublisher.publish(InvoiceCreatedEventPayload.of(debt.getId(), sample));
        } else {
            eventPublisher.publish(RequestCreatedEventPayload.of(debt.getId(), sample));
        }
        return debt;
    }

    @Override
    public void validate(DebtState state) {
        validateBean(state);
    }

    @Override
    protected DebtState cloneState() {
        return DebtSample.from(state);
    }

    public Amount getTotal() {
        return state.getAmount();
        //return state.getDiscount().apply(state.getAmount().plus(state.getTax().apply(state.getAmount())));
    }

    public Amount getDebt() {
        return getTotal().minus(state.getPayedAmount().toImmutable());
    }

    public Amount pay(Payment payment, EventPublisher eventPublisher) {
        condition(() -> DebtStatus.VALIDATED == state.getStatus(), String.format("Debt {} is not validated or already closed", getId()));
        Amount debt = getDebt();
        if (debt.se(payment.getAmount())) {
            eventPublisher.publish(DebtPayedEventPayload.of(getId(), Payment.of(debt, payment.getDate(), payment.getTicket())));
            eventPublisher.publish(DebtClosedEventPayload.of(getId()));
            return payment.getAmount().minus(debt);
        } else {
            eventPublisher.publish(DebtPayedEventPayload.of(getId(), payment));
            return Amount.ZERO;
        }
    }

    public void validate(DebtState modifier, EventPublisher eventPublisher) {
        condition(() -> DebtStatus.CREATED == state.getStatus(), String.format("Debt {} has been already validated", getId()));
        validate(modifier);
        eventPublisher.publish(DebtValidatedEventPayload.of(getId(), DebtSample.from(modifier)));
    }

    public Holder<Debt> save(DebtRepository repository) {
        return repository.save(id, state);
    }

    protected void create(InvoiceCreatedEventPayload event, Instant timestamp) {
        id = event.getDebtId();
        state = DebtSample.from(event.getDebtSample());
        andEvent(event, timestamp);
    }

    protected void validate(DebtValidatedEventPayload event, Instant timestamp) {
        state.validate(event.getDebtState());
        andEvent(event, timestamp);
    }

    protected void pay(DebtPayedEventPayload event, Instant timestamp) {
        state.pay(event.getPayment().getAmount());
        andEvent(event, timestamp);
    }

    protected void close(DebtClosedEventPayload event, Instant timestamp) {
        state.close();
        andEvent(event, timestamp);
    }
}
