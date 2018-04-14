package edu.obya.myoffice.invoicing.query.event.axon;

import edu.noia.myoffice.invoicing.domain.event.DebtEventHandler;
import edu.noia.myoffice.invoicing.domain.event.debt.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.eventhandling.EventHandler;

/**
 * This class is a {@link DebtEventPayload} listener which proxies a {@link DebtEventHandler} instance
 * Event listener aspect provided by Axon
 * Proxy pattern applied
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AxonDebtEventHandler implements DebtEventHandler {

    @NonNull
    DebtEventHandler eventHandler;

    @EventHandler
    public void created(InvoiceCreatedEventPayload event) {
        eventHandler.created(event);
    }

    @EventHandler
    public void validated(DebtValidatedEventPayload event) {
        eventHandler.validated(event);
    }

    @EventHandler
    public void payed(PaymentDoneEventPayload event) {
        eventHandler.payed(event);
    }

    @EventHandler
    public void recalled(RecallEmittedEventPayload event) {
        eventHandler.recalled(event);
    }

    @EventHandler
    public void closed(DebtClosedEventPayload event) {
        eventHandler.closed(event);
    }
}
