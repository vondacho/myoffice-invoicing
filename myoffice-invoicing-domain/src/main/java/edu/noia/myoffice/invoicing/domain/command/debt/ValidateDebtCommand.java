package edu.noia.myoffice.invoicing.domain.command.debt;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidateDebtCommand implements DebtCommand {
    @NonNull
    DebtId debtId;
    DebtSample debtSample;
}
