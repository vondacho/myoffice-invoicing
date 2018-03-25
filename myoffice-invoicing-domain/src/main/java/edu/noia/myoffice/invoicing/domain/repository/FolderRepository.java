package edu.noia.myoffice.invoicing.domain.repository;

import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Folder;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;

import java.util.Optional;

public interface FolderRepository {

    Optional<Holder<Folder>> findOne(FolderId folderId);

    Holder<Folder> save(FolderId id);
}
