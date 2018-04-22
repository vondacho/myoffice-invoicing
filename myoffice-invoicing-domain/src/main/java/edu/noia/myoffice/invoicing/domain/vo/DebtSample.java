package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.aggregate.DefaultDebtState;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtSample implements DefaultDebtState {

    @Setter(value = AccessLevel.PRIVATE)
    FolderId folderId;
    @Setter
    CartId cartId;
    @Setter
    Amount cartAmount;
    @Setter
    Percentage discountRate;
    @Setter
    Percentage taxRate;
    @Setter
    Integer delayDayCount;
    @Setter
    LocalDate delayDate;
    @Setter
    Amount amount;
    @Setter
    String notes;
    @Setter
    DebtStatus status;
    @Setter(value = AccessLevel.PRIVATE)
    Amount payedAmount;
    @Setter(value = AccessLevel.PRIVATE)
    List<Payment> payments;
    @Setter(value = AccessLevel.PRIVATE)
    List<Recall> recalls;

    private DebtSample(@NonNull FolderId folderId) {
        this
                .setFolderId(folderId)
                .setDiscountRate(Percentage.of(0))
                .setTaxRate(Percentage.of(0))
                .setDelayDayCount(0);
    }

    public static DebtSample of(@NonNull FolderId folderId, @NonNull Amount amount) {
        return new DebtSample(folderId)
                .setAmount(amount)
                .setPayedAmount(Amount.ZERO)
                .setPayments(new ArrayList<>())
                .setRecalls(new ArrayList<>());
    }

    public static DebtSample of(@NonNull FolderId folderId, @NonNull CartId cartId, @NonNull Amount amount) {
        return new DebtSample(folderId)
                .setCartId(cartId)
                .setCartAmount(amount)
                .setAmount(amount)
                .setPayedAmount(Amount.ZERO)
                .setPayments(new ArrayList<>())
                .setRecalls(new ArrayList<>());
    }

    public static DebtSample from(DebtState state) {
        return new DebtSample(state.getFolderId())
                .setCartId(state.getCartId())
                .setCartAmount(Amount.from(state.getCartAmount()))
                .setDiscountRate(Percentage.from(state.getDiscountRate()))
                .setTaxRate(Percentage.from(state.getTaxRate()))
                .setDelayDate(state.getDelayDate())
                .setDelayDayCount(state.getDelayDayCount())
                .setNotes(state.getNotes())
                .setAmount(Amount.from(state.getAmount()))
                .setStatus(state.getStatus())
                .setPayedAmount(Amount.from(state.getPayedAmount()))
                .setPayments(state.getPayments() != null ? new ArrayList<>(state.getPayments()) : new ArrayList<>())
                .setRecalls(state.getRecalls() != null ? new ArrayList<>(state.getRecalls()) : new ArrayList<>());
    }
}