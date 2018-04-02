package edu.noia.myoffice.invoicing.query.data.adapter;

import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaDebtState;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaDebtStateRepository;
import edu.obya.myoffice.invoicing.query.repository.DebtStateRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtStateRepositoryAdapter implements DebtStateRepository {

    @NonNull
    JpaDebtStateRepository repository;

    @Override
    public Optional<DebtState> findById(DebtId id) {
        return repository
                .findById(id)
                .map(this::toDebt);
    }

    @Override
    public List<DebtState> findByFolderId(FolderId folderId) {
        return repository
                .findByFolderId(folderId)
                .stream()
                .map(this::toDebt)
                .collect(toList());
    }

    @Override
    public DebtState save(DebtId id, DebtState state) {
        return repository.save(JpaDebtState.of(id, state));
    }

    private DebtState toDebt(JpaDebtState state) {
        return state;
    }
}
