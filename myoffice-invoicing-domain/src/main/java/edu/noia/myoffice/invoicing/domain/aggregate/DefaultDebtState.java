package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.invoicing.domain.vo.Payment;
import edu.noia.myoffice.invoicing.domain.vo.Recall;

public interface DefaultDebtState extends DebtState {

    default DebtState pay(Payment payment) {
        getPayedAmount().plus(payment.getAmount());
        getPayments().add(payment);
        return this;
    }

    default DebtState addRecall(Recall recall) {
        getRecalls().add(recall);
        return this;
    }
}
