package edu.noia.myoffice.invoicing.rest.handler.axon;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.invoicing.domain.event.debt.DebtEventPayload;
import edu.noia.myoffice.invoicing.rest.handler.InvoicingEventBroker;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;

import java.time.Instant;

public class AxonInvoicingEventBrokerProxy extends InvoicingEventBroker {

    @EventHandler(payloadType = DebtEventPayload.class)
    @Override
    public void onSuccess(DebtEventPayload event, @Timestamp Instant timestamp) {
        super.onSuccess(event, timestamp);
    }

    @EventHandler(payloadType = ProblemEventPayload.class)
    @Override
    public void onFailure(ProblemEventPayload event, @Timestamp Instant timestamp) {
        super.onFailure(event, timestamp);
    }
}
