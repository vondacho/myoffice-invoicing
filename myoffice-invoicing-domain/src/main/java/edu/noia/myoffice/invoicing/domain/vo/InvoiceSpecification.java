package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceSpecification {
    @NotNull
    FolderId folderId;
    @NotNull
    CartId cartId;
    @NotNull
    Amount amount;
}
