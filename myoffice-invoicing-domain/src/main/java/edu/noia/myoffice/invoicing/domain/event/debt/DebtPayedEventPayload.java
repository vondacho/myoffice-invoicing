package edu.noia.myoffice.invoicing.domain.event.debt;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtPayedEventPayload implements DebtEventPayload {

    @NonNull
    DebtId debtId;
    @NonNull
    Payment payment;
}
