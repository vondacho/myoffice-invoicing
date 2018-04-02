package edu.noia.myoffice.invoicing.query.data.jpa;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "debts", collectionResourceRel = "debts", itemResourceRel = "debt")
public interface JpaDebtStateRepository
        extends CrudRepository<JpaDebtState, Long>, RevisionRepository<JpaDebtState, Long, Integer> {

    @RestResource(path = "byId", rel = "findById")
    Optional<JpaDebtState> findById(DebtId id);

    @RestResource(path = "byFolder", rel = "findByFolder")
    List<JpaDebtState> findByFolderId(FolderId id);

    @Override
    @RestResource(exported = false)
    JpaDebtState save(JpaDebtState entity);

    @Override
    @RestResource(exported = false)
    void delete(JpaDebtState entity);
}
