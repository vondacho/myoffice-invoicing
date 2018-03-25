package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.MutableAmount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtMutableState;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Accessors(chain = true)
@Getter
@Setter(value = AccessLevel.PRIVATE)
@RequiredArgsConstructor(staticName = "of", access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtMutableSample implements DebtMutableState {

    @NonNull
    FolderId folderId;
    @NonNull
    CartId cartId;
    @NonNull
    Amount cartAmount;
    Percentage taxRate;
    Percentage discountRate;
    Integer delayDayCount;
    LocalDate delayDate;
    String notes;
    MutableAmount payedAmount;
    MutableAmount provisionedAmount;
    DebtStatus status;
    Amount amount;

    public static DebtMutableState of(DebtState state) {
        return DebtMutableSample.of(state.getFolderId(), state.getCartId(), state.getAmount())
                .setDiscountRate(state.getDiscountRate())
                .setTaxRate(state.getTaxRate())
                .setDelayDate(state.getDelayDate())
                .setDelayDayCount(state.getDelayDayCount())
                .setNotes(state.getNotes())
                .setProvisionedAmount(Amount.ZERO.toMutable())
                .setPayedAmount(Amount.ZERO.toMutable())
                .setStatus(DebtStatus.CREATED);
    }

    public void pay(Amount amount) {
        payedAmount.plus(amount);
    }

    @Override
    public void validate(DebtState state) {
        this
                .setDiscountRate(state.getDiscountRate())
                .setTaxRate(state.getTaxRate())
                .setDelayDate(state.getDelayDate())
                .setDelayDayCount(state.getDelayDayCount())
                .setNotes(state.getNotes());
        status = DebtStatus.VALIDATED;
    }

    @Override
    public void close() {
        status = DebtStatus.CLOSED;
    }
}
