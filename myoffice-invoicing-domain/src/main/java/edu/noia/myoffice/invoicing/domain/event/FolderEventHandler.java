package edu.noia.myoffice.invoicing.domain.event;

import edu.noia.myoffice.invoicing.domain.event.folder.*;

public interface FolderEventHandler {

    void created(FolderCreatedEventPayload event);

    void asked(ProvisionAskedEventPayload event);

    void charged(ChargeAccumulatedEventPayload event);

    void provisioned(ProvisionCreatedEventPayload event);

    void consumed(ProvisionUsedEventPayload event);

    void payed(PaymentReceivedEventPayload event);

    void registered(TicketRegisteredEventPayload event);

    void affiliated(FolderJoinedEventPayload event);
}
