package edu.obya.myoffice.invoicing.query.handler.axon;

import edu.noia.myoffice.invoicing.domain.event.folder.*;
import edu.obya.myoffice.invoicing.query.handler.FolderUpdater;
import edu.obya.myoffice.invoicing.query.repository.FolderStateRepository;
import org.axonframework.eventhandling.EventHandler;

public class AxonFolderUpdater extends FolderUpdater {

    public AxonFolderUpdater(FolderStateRepository repository) {
        super(repository);
    }

    @EventHandler
    public void created(FolderCreatedEventPayload event) {
        super.created(event);
    }

    @EventHandler
    public void asked(ProvisionAskedEventPayload event) {
        super.asked(event);
    }

    @EventHandler
    public void charged(ChargeAccumulatedEventPayload event) {
        super.charged(event);
    }

    @EventHandler
    public void provisioned(ProvisionCreatedEventPayload event) {
        super.provisioned(event);
    }

    @EventHandler
    public void consumed(ProvisionUsedEventPayload event) {
        super.consumed(event);
    }

    @EventHandler
    public void payed(PaymentReceivedEventPayload event) {
        super.payed(event);
    }

    @EventHandler
    public void registered(TicketRegisteredEventPayload event) {
        super.registered(event);
    }

    @EventHandler
    public void affiliated(FolderJoinedEventPayload event) {
        super.affiliated(event);
    }
}
