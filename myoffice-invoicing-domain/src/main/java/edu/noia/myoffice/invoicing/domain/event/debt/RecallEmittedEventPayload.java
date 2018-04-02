package edu.noia.myoffice.invoicing.domain.event.debt;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.Recall;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecallEmittedEventPayload implements DebtEventPayload {

    @NonNull
    DebtId debtId;
    @NonNull
    Recall recall;
}
