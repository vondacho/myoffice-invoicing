package edu.noia.myoffice.invoicing.domain.event.folder;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;

public interface FolderEventPayload extends EventPayload {

    FolderId getFolderId();
}
