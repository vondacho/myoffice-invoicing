package edu.noia.myoffice.invoicing.command.data.repository.axon;

import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.command.aggregate.axon.AxonFolder;
import edu.noia.myoffice.invoicing.domain.aggregate.Folder;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.model.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AxonFolderRepository implements FolderRepository {

    @NonNull
    Repository<AxonFolder> repository;

    @Override
    public Optional<Holder<Folder>> findOne(FolderId folderId) {
        return Optional.empty();
    }

    @Override
    public Holder<Folder> save(FolderId id) {
        return null;
    }
}
