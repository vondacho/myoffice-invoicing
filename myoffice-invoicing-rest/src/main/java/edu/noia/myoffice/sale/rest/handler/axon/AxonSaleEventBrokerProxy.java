package edu.noia.myoffice.sale.rest.handler.axon;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.sale.domain.event.cart.CartEventPayload;
import edu.noia.myoffice.sale.rest.handler.SaleEventBroker;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.Timestamp;

import java.time.Instant;

public class AxonSaleEventBrokerProxy extends SaleEventBroker {

    @EventHandler(payloadType = CartEventPayload.class)
    @Override
    public void onSuccess(CartEventPayload event, @Timestamp Instant timestamp) {
        super.onSuccess(event, timestamp);
    }

    @EventHandler(payloadType = ProblemEventPayload.class)
    @Override
    public void onFailure(ProblemEventPayload event, @Timestamp Instant timestamp) {
        super.onFailure(event, timestamp);
    }
}
