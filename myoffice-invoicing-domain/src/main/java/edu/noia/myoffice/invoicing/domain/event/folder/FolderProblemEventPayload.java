package edu.noia.myoffice.invoicing.domain.event.folder;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.exception.Problem;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderProblemEventPayload extends ProblemEventPayload {
    FolderId folderId;

    public FolderProblemEventPayload(@NonNull List<Problem> problems, @NonNull FolderId folderId) {
        super(problems);
        this.folderId = folderId;
    }
}
