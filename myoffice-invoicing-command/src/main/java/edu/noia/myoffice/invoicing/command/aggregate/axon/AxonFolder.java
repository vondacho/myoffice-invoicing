package edu.noia.myoffice.invoicing.command.aggregate.axon;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.aggregate.Folder;
import edu.noia.myoffice.invoicing.domain.event.folder.*;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;

import java.time.Instant;
import java.util.function.Consumer;

public class AxonFolder extends Folder {

    @AggregateIdentifier
    FolderId aggregateId;

    private AxonFolder(FolderId folderId) {
        super(folderId);
    }

    public static AxonFolder create(FolderId folderId) {
        AxonFolder folder = new AxonFolder(folderId);
        AggregateLifecycle.apply(FolderCreatedEventPayload.of(folder.getId()));
        return folder;
    }

    @Override
    public void ask(Amount amount, Consumer<EventPayload> eventPublisher) {
        super.ask(amount, AggregateLifecycle::apply);
    }

    @Override
    public void charge(Amount amount, Consumer<EventPayload> eventPublisher) {
        super.charge(amount, AggregateLifecycle::apply);
    }

    @Override
    public void consume(Amount amount, Consumer<EventPayload> eventPublisher) {
        super.consume(amount, AggregateLifecycle::apply);
    }

    @Override
    public void pay(Payment payment, Consumer<EventPayload> eventPublisher) {
        super.pay(payment, AggregateLifecycle::apply);
    }

    @Override
    public void provision(Payment payment, Consumer<EventPayload> eventPublisher) {
        super.provision(payment, AggregateLifecycle::apply);
    }

    @Override
    public void register(Ticket ticket, Consumer<EventPayload> eventPublisher) {
        super.register(ticket, AggregateLifecycle::apply);
    }

    @EventSourcingHandler
    @Override
    protected void created(FolderCreatedEventPayload event, Instant timestamp) {
        aggregateId = event.getFolderId();
        super.created(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void affiliated(FolderJoinedEventPayload event, Instant timestamp) {
        super.affiliated(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void asked(ProvisionAskedEventPayload event, Instant timestamp) {
        super.asked(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void charged(ChargeAccumulatedEventPayload event, Instant timestamp) {
        super.charged(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void provisioned(ProvisionCreatedEventPayload event, Instant timestamp) {
        super.provisioned(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void consumed(ProvisionUsedEventPayload event, Instant timestamp) {
        super.consumed(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void payed(PaymentReceivedEventPayload event, Instant timestamp) {
        super.payed(event, timestamp);
    }

    @EventSourcingHandler
    @Override
    protected void registered(TicketRegisteredEventPayload event, Instant timestamp) {
        super.registered(event, timestamp);
    }
}
