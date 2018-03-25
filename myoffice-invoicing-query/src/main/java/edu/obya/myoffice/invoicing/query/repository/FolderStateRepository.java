package edu.obya.myoffice.invoicing.query.repository;

import edu.noia.myoffice.sale.domain.aggregate.CartState;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.domain.vo.FolderId;

import java.util.List;
import java.util.Optional;

public interface FolderStateRepository {

    Optional<CartState> findById(CartId id);

    List<CartState> findByFolderId(FolderId folderId);

    CartState save(CartId id, CartState state);
}
