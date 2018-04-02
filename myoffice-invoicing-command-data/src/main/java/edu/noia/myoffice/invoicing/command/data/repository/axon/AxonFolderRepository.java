package edu.noia.myoffice.invoicing.command.data.repository.axon;

import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.command.aggregate.axon.AxonFolder;
import edu.noia.myoffice.invoicing.domain.aggregate.Folder;
import edu.noia.myoffice.invoicing.domain.aggregate.FolderState;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;
import org.axonframework.commandhandling.model.Repository;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AxonFolderRepository implements FolderRepository {

    @NonNull
    Repository<AxonFolder> repository;

    @Override
    public Optional<Holder<Folder>> findOne(FolderId folderId) {
        try {
            return Optional.of(new FolderHolder(repository.load(folderId.toString())));
        } catch (AggregateNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public Holder<Folder> save(FolderId id, FolderState state) {
        try {
            return new FolderHolder(repository.newInstance(() -> AxonFolder.create(id)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @RequiredArgsConstructor
    private class FolderHolder implements Holder<Folder> {
        @NonNull
        Aggregate<AxonFolder> aggregate;

        @Override
        public void execute(Consumer<Folder> action) {
            aggregate.execute(action::accept);
        }
    }
}
