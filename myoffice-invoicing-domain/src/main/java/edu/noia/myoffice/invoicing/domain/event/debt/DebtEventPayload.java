package edu.noia.myoffice.invoicing.domain.event.debt;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;

public interface DebtEventPayload extends EventPayload {

    DebtId getDebtId();
}
