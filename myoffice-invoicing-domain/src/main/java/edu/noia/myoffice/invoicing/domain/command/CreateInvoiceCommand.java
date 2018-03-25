package edu.noia.myoffice.invoicing.domain.command;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.vo.CartId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateInvoiceCommand {
    @NonNull
    FolderId folderId;
    @NonNull
    CartId cartId;
    @NonNull
    Amount cartAmount;
}
