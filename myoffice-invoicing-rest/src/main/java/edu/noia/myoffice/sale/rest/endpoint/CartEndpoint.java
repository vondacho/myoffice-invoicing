package edu.noia.myoffice.sale.rest.endpoint;

import edu.noia.myoffice.common.domain.command.CommandPublisher;
import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.sale.domain.command.cart.CreateCartCommand;
import edu.noia.myoffice.sale.domain.command.cart.OrderCartCommand;
import edu.noia.myoffice.sale.domain.command.item.AddItemToCartCommand;
import edu.noia.myoffice.sale.domain.command.item.RemoveItemFromCartCommand;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.domain.vo.CartItem;
import edu.noia.myoffice.sale.domain.vo.CartItemId;
import edu.noia.myoffice.sale.domain.vo.CartSpecification;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/sale/v1/carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartEndpoint {

    @NonNull
    CommandPublisher commandPublisher;

    @PostMapping
    public ResponseEntity create(@RequestBody CartSpecification cartSpecification) {
        commandPublisher.accept(CreateCartCommand.of(cartSpecification));
        return noContent().build();
    }

    @PutMapping("/{id}/items")
    public ResponseEntity addItem(
            @PathVariable("id") CartId cartId,
            @RequestBody CartItem cartItem) {
        commandPublisher.accept(AddItemToCartCommand.of(cartId, cartItem));
        return noContent().build();
    }

    @DeleteMapping("{id}/items/{itemId}")
    public ResponseEntity removeItem(
            @PathVariable("id") CartId cartId,
            @PathVariable("itemId") CartItemId cartItemId) {
        commandPublisher.accept(RemoveItemFromCartCommand.of(cartId, cartItemId));
        return noContent().build();
    }

    @PutMapping("{id}/ordering")
    public ResponseEntity order(@PathVariable("id") CartId cartId) {
        commandPublisher.accept(OrderCartCommand.of(cartId));
        return noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") CartId cartId) {
        return notFound().build();
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(CartId.class,
                new IdentifiantPropertyEditorSupport<>(s -> CartId.of(UUID.fromString(s))));
        binder.registerCustomEditor(CartItemId.class,
                new IdentifiantPropertyEditorSupport<>(s -> CartItemId.of(UUID.fromString(s))));
    }
}
