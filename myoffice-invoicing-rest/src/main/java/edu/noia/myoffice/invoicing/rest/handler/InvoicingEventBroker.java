package edu.noia.myoffice.invoicing.rest.handler;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.broker.DefaultBroker;
import edu.noia.myoffice.invoicing.domain.event.debt.DebtEventPayload;
import edu.noia.myoffice.invoicing.domain.event.folder.FolderEventPayload;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
import edu.noia.myoffice.invoicing.rest.processor.Processors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import java.time.Instant;
import java.util.UUID;

import static edu.noia.myoffice.invoicing.rest.processor.Processors.*;

@Slf4j
public class InvoicingEventBroker extends DefaultBroker<InvoicingEvent, UUID> {

    @Autowired
    ResourceProcessor<Resource<DebtId>> debtHateoasProcessor;
    @Autowired
    ResourceProcessor<Resource<FolderId>> folderHateoasProcessor;

    public void onSuccess(FolderEventPayload event, Instant timestamp) {
        toFolderEvent(timestamp)
                .apply(event)
                .flatMap(Processors.addHateoasToFolder(folderHateoasProcessor)).ifPresent(this::accept);
    }

    public void onSuccess(DebtEventPayload event, Instant timestamp) {
        toDebtEvent(timestamp)
                .apply(event)
                .flatMap(Processors.addHateoasToDebt(debtHateoasProcessor)).ifPresent(this::accept);
    }

    public void onFailure(ProblemEventPayload event, Instant timestamp) {
        toProblemEvent(timestamp).apply(event).ifPresent(this::accept);
    }
}
