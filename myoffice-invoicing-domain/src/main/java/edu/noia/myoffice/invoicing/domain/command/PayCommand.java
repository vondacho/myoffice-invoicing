package edu.noia.myoffice.invoicing.domain.command;

import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayCommand {

    @NonNull
    FolderId folderId;
    @NonNull
    Payment payment;
}
