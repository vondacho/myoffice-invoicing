package edu.noia.myoffice.invoicing.query.data.adapter;

import edu.noia.myoffice.invoicing.domain.aggregate.FolderState;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaFolderState;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaFolderStateRepository;
import edu.obya.myoffice.invoicing.query.repository.FolderStateRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderStateRepositoryAdapter implements FolderStateRepository {

    @NonNull
    JpaFolderStateRepository repository;

    @Override
    public Optional<FolderState> findById(FolderId id) {
        return repository
                .findById(id)
                .map(this::toFolder);
    }

    @Override
    public FolderState save(FolderId id, FolderState state) {
        return repository.save(JpaFolderState.of(id, state));
    }

    private FolderState toFolder(JpaFolderState state) {
        return state;
    }
}
