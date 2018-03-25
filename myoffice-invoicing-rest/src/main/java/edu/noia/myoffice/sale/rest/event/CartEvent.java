package edu.noia.myoffice.sale.rest.event;

import edu.noia.myoffice.sale.domain.vo.CartId;
import org.springframework.hateoas.Resource;

import java.time.Instant;

public class CartEvent extends SaleEvent<Resource<CartId>> {

    public CartEvent(Instant timestamp, Class eventClass, Resource<CartId> payload) {
        super(timestamp, eventClass, payload);
    }
}
