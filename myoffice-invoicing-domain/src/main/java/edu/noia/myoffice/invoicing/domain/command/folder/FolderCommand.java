package edu.noia.myoffice.invoicing.domain.command.folder;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;

public interface FolderCommand extends Command {
    FolderId getFolderId();
}
