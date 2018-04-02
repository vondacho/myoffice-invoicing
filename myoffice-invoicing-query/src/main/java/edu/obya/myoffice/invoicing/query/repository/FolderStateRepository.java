package edu.obya.myoffice.invoicing.query.repository;

import edu.noia.myoffice.invoicing.domain.aggregate.FolderState;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;

import java.util.Optional;

public interface FolderStateRepository {

    Optional<FolderState> findById(FolderId id);

    FolderState save(FolderId id, FolderState state);
}
