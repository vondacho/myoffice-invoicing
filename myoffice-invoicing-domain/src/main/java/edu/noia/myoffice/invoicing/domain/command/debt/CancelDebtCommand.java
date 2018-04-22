package edu.noia.myoffice.invoicing.domain.command.debt;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelDebtCommand implements DebtCommand {
    @NonNull
    DebtId debtId;
}
