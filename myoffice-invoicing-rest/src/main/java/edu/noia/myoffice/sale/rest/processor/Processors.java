package edu.noia.myoffice.sale.rest.processor;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.processor.Filter;
import edu.noia.myoffice.common.util.processor.Processor;
import edu.noia.myoffice.sale.domain.event.cart.CartEventPayload;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.rest.event.CartEvent;
import edu.noia.myoffice.sale.rest.event.SaleEvent;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import java.time.Instant;
import java.util.Optional;

public class Processors {

    private Processors() {
    }

    public static Processor<ProblemEventPayload, SaleEvent> toProblemEvent(Instant timestamp) {
        return event -> Optional.of(new SaleEvent(timestamp, event.getClass(), event));
    }

    public static Processor<CartEventPayload, CartEvent> toCartEvent(Instant timestamp) {
        return event -> Optional.of(new CartEvent(timestamp, event.getClass(), new Resource<>(event.getCartId())));
    }

    public static Processor<CartEvent, CartEvent> addHateoas(ResourceProcessor<Resource<CartId>> processor) {
        return event -> {
            processor.process(event.getPayload());
            return Optional.of(event);
        };
    }

    public static Filter<SaleEvent> forCart(CartId cartId) {
        return event -> Optional.of(event)
                .filter(e -> e instanceof CartEvent)
                .map(e -> (CartEvent) e)
                .filter(e -> e.getPayload().getContent().getId().equals(cartId.getId()))
                .map(e -> (SaleEvent) e);
    }
}
