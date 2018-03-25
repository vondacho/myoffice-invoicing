package edu.noia.myoffice.sale.rest.handler;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.broker.DefaultBroker;
import edu.noia.myoffice.sale.domain.event.cart.CartEventPayload;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.rest.event.SaleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import java.time.Instant;
import java.util.UUID;

import static edu.noia.myoffice.sale.rest.processor.Processors.*;

@Slf4j
public class SaleEventBroker extends DefaultBroker<SaleEvent, UUID> {

    @Autowired
    ResourceProcessor<Resource<CartId>> hateoasProcessor;

    public void onSuccess(CartEventPayload event, Instant timestamp) {
        toCartEvent(timestamp)
                .apply(event)
                .flatMap(addHateoas(hateoasProcessor)).ifPresent(this::accept);
    }

    public void onFailure(ProblemEventPayload event, Instant timestamp) {
        toProblemEvent(timestamp).apply(event).ifPresent(this::accept);
    }
}
