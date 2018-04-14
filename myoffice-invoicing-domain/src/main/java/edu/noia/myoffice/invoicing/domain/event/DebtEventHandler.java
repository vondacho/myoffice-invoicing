package edu.noia.myoffice.invoicing.domain.event;

import edu.noia.myoffice.invoicing.domain.event.debt.*;

public interface DebtEventHandler {

    void created(InvoiceCreatedEventPayload event);

    void validated(DebtValidatedEventPayload event);

    void payed(PaymentDoneEventPayload event);

    void recalled(RecallEmittedEventPayload event);

    void closed(DebtClosedEventPayload event);
}