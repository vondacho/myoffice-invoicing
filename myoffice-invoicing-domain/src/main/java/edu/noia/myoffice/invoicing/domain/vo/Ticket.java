package edu.noia.myoffice.invoicing.domain.vo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @NonNull
    String number;
    @NonNull
    FolderId folderId;
}
