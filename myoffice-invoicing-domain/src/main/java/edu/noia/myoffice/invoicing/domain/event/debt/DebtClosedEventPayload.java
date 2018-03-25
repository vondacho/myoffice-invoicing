package edu.noia.myoffice.invoicing.domain.event.debt;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtClosedEventPayload implements DebtEventPayload {

    @NonNull
    DebtId debtId;
}
