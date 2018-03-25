package edu.noia.myoffice.invoicing.domain.event.debt;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceCreatedEventPayload implements DebtEventPayload {

    @NonNull
    DebtId debtId;
    @NonNull
    DebtSample debtSample;
}
