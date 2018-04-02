package edu.obya.myoffice.invoicing.query.handler.axon;

import edu.noia.myoffice.invoicing.domain.event.debt.*;
import edu.obya.myoffice.invoicing.query.handler.DebtUpdater;
import edu.obya.myoffice.invoicing.query.repository.DebtStateRepository;
import org.axonframework.eventhandling.EventHandler;

public class AxonDebtUpdater extends DebtUpdater {

    public AxonDebtUpdater(DebtStateRepository repository) {
        super(repository);
    }

    @EventHandler
    public void created(InvoiceCreatedEventPayload event) {
        super.created(event);
    }

    @EventHandler
    public void validated(DebtValidatedEventPayload event) {
        super.validated(event);
    }

    @EventHandler
    public void payed(PaymentDoneEventPayload event) {
        super.payed(event);
    }

    @EventHandler
    public void recalled(RecallEmittedEventPayload event) {
        super.recalled(event);
    }

    @EventHandler
    public void closed(DebtClosedEventPayload event) {
        super.closed(event);
    }
}
