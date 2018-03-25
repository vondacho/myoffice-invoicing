package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtSpecification {

    FolderId folderId;
    CartId cartId;
    Amount cartAmount;
    Percentage discountRate;
    Percentage taxRate;
    Integer delayDayCount;
    LocalDate delayDate;
    String notes;
    Amount amount;
}