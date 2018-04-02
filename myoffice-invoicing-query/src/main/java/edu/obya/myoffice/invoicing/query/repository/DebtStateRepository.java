package edu.obya.myoffice.invoicing.query.repository;


import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;

import java.util.List;
import java.util.Optional;

public interface DebtStateRepository {

    Optional<DebtState> findById(DebtId id);

    List<DebtState> findByFolderId(FolderId folderId);

    DebtState save(DebtId id, DebtState state);
}
