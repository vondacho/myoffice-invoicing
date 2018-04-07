package edu.noia.myoffice.invoicing.command.aggregate.axon;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.event.debt.*;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventhandling.Timestamp;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.Instant;
import java.util.function.Consumer;

import static edu.noia.myoffice.invoicing.domain.vo.DebtStatus.CREATED;

@EqualsAndHashCode(callSuper = true)
@Aggregate
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AxonDebt extends Debt {

    @AggregateIdentifier
    DebtId aggregateId;

    private AxonDebt(DebtState state) {
        super(state);
    }

    public static AxonDebt create(DebtState state) {
        DebtSample sample = DebtSample.from(validateBean(state)).setStatus(CREATED);
        AxonDebt debt = new AxonDebt(state);
        if (state.getCartId() != null) {
            AggregateLifecycle.apply(InvoiceCreatedEventPayload.of(debt.getId(), sample));
        } else {
            AggregateLifecycle.apply(RequestCreatedEventPayload.of(debt.getId(), sample));
        }
        return debt;
    }

    @Override
    public Amount pay(Payment payment, Consumer<EventPayload> eventPublisher) {
        return super.pay(payment, AggregateLifecycle::apply);
    }

    @Override
    public void validate(DebtState modifier, Consumer<EventPayload> eventPublisher) {
        super.validate(modifier, AggregateLifecycle::apply);
    }

    @Override
    public Holder<Debt> save(DebtRepository repository) {
        return null;
    }

    @EventSourcingHandler
    @Override
    protected void created(InvoiceCreatedEventPayload event, @Timestamp Instant timestamp) {
        aggregateId = event.getDebtId();
        super.created(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void validated(DebtValidatedEventPayload event, @Timestamp Instant timestamp) {
        super.validated(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void payed(PaymentDoneEventPayload event, @Timestamp Instant timestamp) {
        super.payed(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void recalled(RecallEmittedEventPayload event, @Timestamp Instant timestamp) {
        super.recalled(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void closed(DebtClosedEventPayload event, @Timestamp Instant timestamp) {
        super.closed(event, timestamp);
    }
}
