package edu.noia.myoffice.invoicing.domain.repository;

import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;

import java.util.Optional;

public interface DebtRepository {

    Optional<Holder<Debt>> findOne(DebtId debtId);

    Holder<Debt> save(DebtId id, DebtState state);
}
