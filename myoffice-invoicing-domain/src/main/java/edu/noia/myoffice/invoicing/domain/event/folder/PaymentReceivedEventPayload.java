package edu.noia.myoffice.invoicing.domain.event.folder;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentReceivedEventPayload implements FolderEventPayload {

    @NonNull
    FolderId folderId;
    @NonNull
    Payment payment;
    DebtId debtId;
}
