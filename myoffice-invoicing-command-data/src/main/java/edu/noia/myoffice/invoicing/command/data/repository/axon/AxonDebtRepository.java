package edu.noia.myoffice.invoicing.command.data.repository.axon;

import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.command.aggregate.axon.AxonDebt;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
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
public class AxonDebtRepository implements DebtRepository {

    @NonNull
    Repository<AxonDebt> repository;

    @Override
    public Optional<Holder<Debt>> findOne(DebtId debtId) {
        try {
            return Optional.of(new DebtHolder(repository.load(debtId.toString())));
        } catch (AggregateNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public Holder<Debt> save(DebtId id, DebtState state) {
        try {
            return new DebtHolder(repository.newInstance(() -> AxonDebt.create(state)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @RequiredArgsConstructor
    private class DebtHolder implements Holder<Debt> {
        @NonNull
        Aggregate<AxonDebt> aggregate;

        @Override
        public void execute(Consumer<Debt> action) {
            aggregate.execute(action::accept);
        }
    }
}
