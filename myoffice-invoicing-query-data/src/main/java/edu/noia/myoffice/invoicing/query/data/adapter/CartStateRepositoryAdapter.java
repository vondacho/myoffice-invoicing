package edu.noia.myoffice.invoicing.query.data.adapter;

import edu.noia.myoffice.sale.domain.aggregate.CartState;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.domain.vo.FolderId;
import edu.noia.myoffice.sale.query.data.jpa.JpaCartState;
import edu.noia.myoffice.sale.query.data.jpa.JpaCartStateRepository;
import edu.noia.myoffice.sale.query.repository.CartStateRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartStateRepositoryAdapter implements CartStateRepository {

    @NonNull
    JpaCartStateRepository repository;

    @Override
    public Optional<CartState> findById(CartId id) {
        return repository
                .findById(id)
                .map(this::toCart);
    }

    @Override
    public List<CartState> findByFolderId(FolderId folderId) {
        return repository
                .findByFolderId(folderId)
                .stream()
                .map(this::toCart)
                .collect(toList());
    }

    @Override
    public CartState save(CartId id, CartState state) {
        return repository.save(JpaCartState.of(id, state));
    }

    private CartState toCart(JpaCartState state) {
        return state;
    }
}
