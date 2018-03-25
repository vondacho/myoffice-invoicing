package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.BaseEntity;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.event.folder.*;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.FolderSample;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.List;

import static edu.noia.myoffice.common.util.validation.Rule.condition;
import static edu.noia.myoffice.invoicing.domain.aggregate.Debt.validateBean;
import static java.util.Collections.unmodifiableList;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Folder extends BaseEntity<Folder, FolderId, FolderSample> {

    protected Folder(FolderId folderId) {
        super(folderId, new FolderSample());
    }

    public static Folder of(FolderId folderId, EventPublisher eventPublisher) {
        Folder folder = new Folder(folderId);
        eventPublisher.publish(FolderCreatedEventPayload.of(folder.getId()));
        return folder;
    }

    public List<Ticket> getTickets() {
        return unmodifiableList(state.getTickets());
    }

    public Amount getDebt() {
        return state.getDebtAmount().toImmutable();
    }

    public Amount getProvisions() {
        return state.getProvisionedAmount().toImmutable();
    }

    public void ask(Amount amount, EventPublisher eventPublisher) {
        condition(() -> getDebt().gt(Amount.ZERO), String.format("Folder {} has no overdraft", getId()));
        eventPublisher.publish(ProvisionAskedEventPayload.of(getId(), amount));
    }

    public void charge(Amount amount, EventPublisher eventPublisher) {
        eventPublisher.publish(ChargeAccumulatedEventPayload.of(getId(), amount));
    }

    public void consume(Amount amount, EventPublisher eventPublisher) {
        condition(() -> getProvisions().gt(Amount.ZERO), String.format("Folder {} has no overdraft", getId()));
        eventPublisher.publish(ProvisionUsedEventPayload.of(getId(), amount));
    }

    public void pay(Payment payment, EventPublisher eventPublisher) {
        eventPublisher.publish(PaymentReceivedEventPayload.of(getId(), payment));
    }

    public void provision(Payment payment, EventPublisher eventPublisher) {
        eventPublisher.publish(ProvisionCreatedEventPayload.of(getId(), payment));
    }

    public void register(Ticket ticket, EventPublisher eventPublisher) {
        condition(() -> !getTickets().contains(ticket),
                String.format("Folder {} already contains ticket {}", getId(), ticket.getNumber()));
        eventPublisher.publish(TicketRegisteredEventPayload.of(getId(), ticket));
    }

    public Holder<Folder> save(FolderRepository repository) {
        return repository.save(id);
    }

    protected void create(FolderCreatedEventPayload event, Instant timestamp) {
        id = event.getFolderId();
        state = new FolderSample();
        andEvent(event, timestamp);
    }

    protected void affiliate(FolderJoinedEventPayload event, Instant timestamp) {
        state.affiliate(event.getCustomerId());
        andEvent(event, timestamp);
    }

    protected void ask(ProvisionAskedEventPayload event, Instant timestamp) {
        andEvent(event, timestamp);
    }

    protected void charge(ChargeAccumulatedEventPayload event, Instant timestamp) {
        state.charge(event.getAmount());
        andEvent(event, timestamp);
    }

    protected void provision(ProvisionCreatedEventPayload event, Instant timestamp) {
        state.provision(event.getPayment().getAmount());
        andEvent(event, timestamp);
    }

    protected void consume(ProvisionUsedEventPayload event, Instant timestamp) {
        state.consume(event.getAmount());
        andEvent(event, timestamp);
    }

    protected void pay(PaymentReceivedEventPayload event, Instant timestamp) {
        state.consume(event.getPayment().getAmount());
        andEvent(event, timestamp);
    }

    protected void register(TicketRegisteredEventPayload event, Instant timestamp) {
        state.addTicket(event.getTicket());
        andEvent(event, timestamp);
    }

    @Override
    protected FolderSample cloneState() {
        return FolderSample.from(state);
    }

    @Override
    public void validate(FolderSample state) {
        validateBean(state);
    }
}
