package edu.obya.myoffice.invoicing.query.event.axon;

import edu.noia.myoffice.invoicing.domain.event.FolderEventHandler;
import edu.noia.myoffice.invoicing.domain.event.folder.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;

/**
 * This class is a {@link FolderEventPayload} listener which proxies a {@link FolderEventHandler} instance
 * Event listener aspect provided by Axon
 * Proxy pattern applied
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AxonFolderEventHandler implements FolderEventHandler {

    @NonNull
    FolderEventHandler eventHandler;

    @EventHandler
    public void created(FolderCreatedEventPayload event) {
        eventHandler.created(event);
    }

    @EventHandler
    public void asked(ProvisionAskedEventPayload event) {
        eventHandler.asked(event);
    }

    @EventHandler
    public void charged(ChargeAccumulatedEventPayload event) {
        eventHandler.charged(event);
    }

    @EventHandler
    public void provisioned(ProvisionCreatedEventPayload event) {
        eventHandler.provisioned(event);
    }

    @EventHandler
    public void consumed(ProvisionUsedEventPayload event) {
        eventHandler.consumed(event);
    }

    @EventHandler
    public void payed(PaymentReceivedEventPayload event) {
        eventHandler.payed(event);
    }

    @EventHandler
    public void registered(TicketRegisteredEventPayload event) {
        eventHandler.registered(event);
    }

    @EventHandler
    public void affiliated(FolderJoinedEventPayload event) {
        eventHandler.affiliated(event);
    }
}
