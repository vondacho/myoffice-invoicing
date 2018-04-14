package edu.obya.myoffice.invoicing.query.handler;

import edu.noia.myoffice.invoicing.domain.event.FolderEventHandler;
import edu.noia.myoffice.invoicing.domain.event.folder.*;
import edu.noia.myoffice.invoicing.domain.vo.Affiliate;
import edu.noia.myoffice.invoicing.domain.vo.FolderSample;
import edu.obya.myoffice.invoicing.query.repository.FolderStateRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderUpdater implements FolderEventHandler {

    @NonNull
    FolderStateRepository repository;

    public void created(FolderCreatedEventPayload event) {
        repository.save(event.getFolderId(), new FolderSample());
    }

    public void asked(ProvisionAskedEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.ask(event.getAmount()));
    }

    public void charged(ChargeAccumulatedEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.charge(event.getAmount()));
    }

    public void provisioned(ProvisionCreatedEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.provision(event.getPayment().getAmount()));
    }

    public void consumed(ProvisionUsedEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.consume(event.getAmount()));
    }

    public void payed(PaymentReceivedEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.pay(event.getPayment().getAmount()));
    }

    public void registered(TicketRegisteredEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.addTicket(event.getTicket()));
    }

    public void affiliated(FolderJoinedEventPayload event) {
        repository
                .findById(event.getFolderId())
                .ifPresent(state -> state.addAffiliate(Affiliate.of(event.getCustomerId())));
    }
}
