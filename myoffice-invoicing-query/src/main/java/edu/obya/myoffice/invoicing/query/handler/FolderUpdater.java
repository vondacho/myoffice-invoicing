package edu.obya.myoffice.invoicing.query.handler;

import edu.noia.myoffice.sale.domain.event.cart.CartCreatedEventPayload;
import edu.noia.myoffice.sale.domain.event.cart.CartInvoicedEventPayload;
import edu.noia.myoffice.sale.domain.event.cart.CartOrderedEventPayload;
import edu.noia.myoffice.sale.domain.event.item.ItemAddedToCartEventPayload;
import edu.noia.myoffice.sale.domain.event.item.ItemRemovedFromCartEventPayload;
import edu.noia.myoffice.sale.domain.vo.CartSample;
import edu.noia.myoffice.sale.query.repository.CartStateRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class FolderUpdater {

    @NonNull
    CartStateRepository repository;

    public void created(CartCreatedEventPayload event) {
        repository.save(event.getCartId(), CartSample.from(event.getCartSpecification()));
    }

    public void itemAdded(ItemAddedToCartEventPayload event) {
        repository
                .findById(event.getCartId())
                .ifPresent(cart -> cart.add(event.getCartItem()));
    }

    public void itemRemoved(ItemRemovedFromCartEventPayload event) {
        repository
                .findById(event.getCartId())
                .ifPresent(cart -> cart.remove(event.getCartItemId()));
    }

    public void ordered(CartOrderedEventPayload event) {
        repository
                .findById(event.getCartId())
                .ifPresent(cart -> cart.setOrderId(event.getOrderId()));
    }

    public void invoiced(CartInvoicedEventPayload event) {
        repository
                .findById(event.getCartId())
                .ifPresent(cart -> cart.setInvoiceId(event.getInvoiceId()));
    }
}
