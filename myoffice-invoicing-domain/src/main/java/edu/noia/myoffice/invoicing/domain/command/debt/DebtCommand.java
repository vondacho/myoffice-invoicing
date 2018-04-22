package edu.noia.myoffice.invoicing.domain.command.debt;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;

public interface DebtCommand extends Command {
    DebtId getDebtId();
}
