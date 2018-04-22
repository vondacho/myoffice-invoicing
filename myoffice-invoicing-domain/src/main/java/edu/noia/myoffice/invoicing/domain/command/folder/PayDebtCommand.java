package edu.noia.myoffice.invoicing.domain.command.folder;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayDebtCommand implements FolderCommand {
    @NonNull
    FolderId folderId;
    @NonNull
    Payment payment;
    @NonNull
    List<DebtId> debts;
}
