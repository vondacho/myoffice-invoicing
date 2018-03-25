package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.MutableAmount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Accessors(chain = true)
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtSample implements DebtState {

    @NonNull
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

    public static DebtSample of(@NonNull FolderId folderId, @NonNull Amount amount) {
        return new DebtSample(folderId).setAmount(amount);
    }

    public static DebtSample of(@NonNull FolderId folderId, @NonNull CartId cartId, @NonNull Amount cartAmount) {
        return new DebtSample(folderId).setCartId(cartId).setCartAmount(cartAmount);
    }

    public static DebtSample from(DebtState state) {
        return new DebtSample(state.getFolderId())
                .setCartId(state.getCartId())
                .setCartAmount(state.getCartAmount())
                .setDiscountRate(state.getDiscountRate())
                .setTaxRate(state.getTaxRate())
                .setDelayDate(state.getDelayDate())
                .setDelayDayCount(state.getDelayDayCount())
                .setNotes(state.getNotes())
                .setAmount(state.getAmount());
    }

    @Override
    public MutableAmount getPayedAmount() {
        return null;
    }

    @Override
    public void pay(Amount amount) {

    }

    @Override
    public DebtStatus getStatus() {
        return null;
    }

    @Override
    public void validate(DebtState debtState) {

    }

    @Override
    public void close() {

    }
}