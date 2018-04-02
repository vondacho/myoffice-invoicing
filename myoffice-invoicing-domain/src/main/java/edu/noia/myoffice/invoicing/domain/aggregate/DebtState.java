package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.EntityState;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.invoicing.domain.vo.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;

public interface DebtState extends EntityState {

    @NotNull
    FolderId getFolderId();

    @NotNull
    CartId getCartId();

    @NotNull
    Amount getCartAmount();

    Percentage getTaxRate();

    Percentage getDiscountRate();

    Integer getDelayDayCount();

    LocalDate getDelayDate();

    String getNotes();

    Amount getAmount();

    default Amount getPayedAmount() {
        return Amount.ZERO;
    }

    DebtStatus getStatus();

    DebtState setStatus(DebtStatus status);

    default List<Payment> getPayments() {
        return emptyList();
    }

    default List<Recall> getRecalls() {
        return emptyList();
    }

    DebtState pay(Payment payment);

    DebtState addRecall(Recall recall);
}
