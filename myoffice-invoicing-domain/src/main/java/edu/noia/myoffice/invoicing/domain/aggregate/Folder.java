package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.BaseEntity;
import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.common.util.validation.BeanValidator;
import edu.noia.myoffice.invoicing.domain.event.folder.*;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.Function;

import static edu.noia.myoffice.common.util.validation.Rule.condition;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Folder extends BaseEntity<Folder, FolderId, FolderSample> {

    protected Folder(FolderId folderId) {
        super(folderId, new FolderSample());
    }

    private static <T> T validateBean(T state) {
        return BeanValidator.validate(state);
    }

    protected static Folder create(FolderId folderId, Consumer<EventPayload> eventPublisher, Function<FolderId, Folder> factory) {
        Folder folder = factory.apply(folderId);
        eventPublisher.accept(FolderCreatedEventPayload.of(folder.getId()));
        return folder;
    }

    public static Folder create(FolderId folderId, Consumer<EventPayload> eventPublisher) {
        return create(folderId, eventPublisher, Folder::new);
    }

    public void ask(Amount amount, Consumer<EventPayload> eventPublisher) {
        condition(() -> state.getDebtAmount().gt(Amount.ZERO), String.format("Folder %s has no overdraft", getId()));
        eventPublisher.accept(ProvisionAskedEventPayload.of(getId(), amount));
    }

    public void charge(Amount amount, Consumer<EventPayload> eventPublisher) {
        eventPublisher.accept(ChargeAccumulatedEventPayload.of(getId(), amount));
    }

    public void consume(Amount amount, Consumer<EventPayload> eventPublisher) {
        condition(() -> state.getProvisionedAmount().gt(Amount.ZERO), String.format("Folder %s has no overdraft", getId()));
        eventPublisher.accept(ProvisionUsedEventPayload.of(getId(), amount));
    }

    public void pay(Payment payment, Consumer<EventPayload> eventPublisher) {
        eventPublisher.accept(PaymentReceivedEventPayload.of(getId(), payment));
    }

    public void provision(Payment payment, Consumer<EventPayload> eventPublisher) {
        eventPublisher.accept(ProvisionCreatedEventPayload.of(getId(), payment));
    }

    public void register(Ticket ticket, Consumer<EventPayload> eventPublisher) {
        condition(() -> !state.getTickets().contains(ticket),
                String.format("Folder %s already contains ticket %s", getId(), ticket.getId()));
        eventPublisher.accept(TicketRegisteredEventPayload.of(getId(), ticket));
    }

    public Holder<Folder> save(FolderRepository repository) {
        return repository.save(id, state);
    }

    protected void created(FolderCreatedEventPayload event, Instant timestamp) {
        id = event.getFolderId();
        state = new FolderSample();
        andEvent(event, timestamp);
    }

    protected void asked(ProvisionAskedEventPayload event, Instant timestamp) {
        state.ask(event.getAmount());
        andEvent(event, timestamp);
    }

    protected void charged(ChargeAccumulatedEventPayload event, Instant timestamp) {
        state.charge(event.getAmount());
        andEvent(event, timestamp);
    }

    protected void provisioned(ProvisionCreatedEventPayload event, Instant timestamp) {
        state.provision(event.getPayment().getAmount());
        andEvent(event, timestamp);
    }

    protected void consumed(ProvisionUsedEventPayload event, Instant timestamp) {
        state.consume(event.getAmount());
        andEvent(event, timestamp);
    }

    protected void payed(PaymentReceivedEventPayload event, Instant timestamp) {
        state.consume(event.getPayment().getAmount());
        andEvent(event, timestamp);
    }

    protected void registered(TicketRegisteredEventPayload event, Instant timestamp) {
        state.addTicket(event.getTicket());
        andEvent(event, timestamp);
    }

    protected void affiliated(FolderJoinedEventPayload event, Instant timestamp) {
        state.addAffiliate(Affiliate.of(event.getCustomerId()));
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
