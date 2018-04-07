package edu.noia.myoffice.invoicing.rest.event;

import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import org.springframework.hateoas.Resource;

import java.time.Instant;

public class FolderEvent extends InvoicingEvent<Resource<FolderId>> {

    public FolderEvent(Instant timestamp, Class eventClass, Resource<FolderId> payload) {
        super(timestamp, eventClass, payload);
    }
}
