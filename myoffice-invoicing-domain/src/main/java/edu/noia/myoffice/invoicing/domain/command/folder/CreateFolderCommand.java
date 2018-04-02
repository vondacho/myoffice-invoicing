package edu.noia.myoffice.invoicing.domain.command.folder;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateFolderCommand implements Command {
    @NonNull
    FolderId folderId;
}
