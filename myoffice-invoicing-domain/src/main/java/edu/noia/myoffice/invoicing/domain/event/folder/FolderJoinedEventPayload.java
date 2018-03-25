package edu.noia.myoffice.invoicing.domain.event.folder;

import edu.noia.myoffice.invoicing.domain.vo.CustomerId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderJoinedEventPayload implements FolderEventPayload {

    @NonNull
    FolderId folderId;
    @NonNull
    CustomerId customerId;
}
