package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.EntityState;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.MutableAmount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.invoicing.domain.vo.CartId;
import edu.noia.myoffice.invoicing.domain.vo.DebtStatus;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    MutableAmount getPayedAmount();

    void pay(Amount amount);

    DebtStatus getStatus();

    void validate(DebtState debtState);

    void close();
}
