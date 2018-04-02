package edu.noia.myoffice.invoicing.query.data.jpa;

import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "folders", collectionResourceRel = "folders", itemResourceRel = "folder")
public interface JpaFolderStateRepository
        extends CrudRepository<JpaFolderState, Long>, RevisionRepository<JpaFolderState, Long, Integer> {

    @RestResource(path = "byId", rel = "findById")
    Optional<JpaFolderState> findById(FolderId id);

    @Override
    @RestResource(exported = false)
    JpaFolderState save(JpaFolderState entity);

    @Override
    @RestResource(exported = false)
    void delete(JpaFolderState entity);
}
