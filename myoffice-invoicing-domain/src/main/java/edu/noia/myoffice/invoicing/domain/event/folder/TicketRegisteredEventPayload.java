package edu.noia.myoffice.invoicing.domain.event.folder;

import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import lombok.*;
import lombok.experimental.FieldDefaults;

@ToString
@Getter
@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TicketRegisteredEventPayload implements FolderEventPayload {

    @NonNull
    FolderId folderId;
    @NonNull
    Ticket ticket;
}
