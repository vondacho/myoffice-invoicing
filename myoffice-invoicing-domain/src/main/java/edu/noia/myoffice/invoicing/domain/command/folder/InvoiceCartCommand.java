package edu.noia.myoffice.invoicing.domain.command.folder;

import edu.noia.myoffice.common.domain.command.Command;
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
public class InvoiceCartCommand implements Command {

    @NonNull
    CartId cartId;
    @NonNull
    FolderId folderId;
    @NonNull
    Amount amount;
}