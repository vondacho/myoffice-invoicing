package edu.noia.myoffice.invoicing.rest.processor;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.processor.Filter;
import edu.noia.myoffice.common.util.processor.Processor;
import edu.noia.myoffice.invoicing.domain.event.debt.DebtEventPayload;
import edu.noia.myoffice.invoicing.domain.event.folder.FolderEventPayload;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.rest.event.DebtEvent;
import edu.noia.myoffice.invoicing.rest.event.FolderEvent;
import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import java.time.Instant;
import java.util.Optional;

public class Processors {

    private Processors() {
    }

    public static Processor<ProblemEventPayload, InvoicingEvent> toProblemEvent(Instant timestamp) {
        return event -> Optional.of(new InvoicingEvent(timestamp, event.getClass(), event));
    }

    public static Processor<DebtEventPayload, DebtEvent> toDebtEvent(Instant timestamp) {
        return event -> Optional.of(new DebtEvent(timestamp, event.getClass(), new Resource<>(event.getDebtId())));
    }

    public static Processor<FolderEventPayload, FolderEvent> toFolderEvent(Instant timestamp) {
        return event -> Optional.of(new FolderEvent(timestamp, event.getClass(), new Resource<>(event.getFolderId())));
    }

    public static Processor<DebtEvent, DebtEvent> addHateoasToDebt(ResourceProcessor<Resource<DebtId>> processor) {
        return event -> {
            processor.process(event.getPayload());
            return Optional.of(event);
        };
    }

    public static Processor<FolderEvent, FolderEvent> addHateoasToFolder(ResourceProcessor<Resource<FolderId>> processor) {
        return event -> {
            processor.process(event.getPayload());
            return Optional.of(event);
        };
    }

    public static Filter<InvoicingEvent> forDebt(DebtId debtId) {
        return event -> Optional.of(event)
                .filter(e -> e instanceof DebtEvent)
                .map(e -> (DebtEvent) e)
                .filter(e -> e.getPayload().getContent().getId().equals(debtId.getId()))
                .map(e -> (InvoicingEvent) e);
    }

    public static Filter<InvoicingEvent> forFolder(FolderId folderId) {
        return event -> Optional.of(event)
                .filter(e -> e instanceof FolderEvent)
                .map(e -> (FolderEvent) e)
                .filter(e -> e.getPayload().getContent().getId().equals(folderId.getId()))
                .map(e -> (InvoicingEvent) e);
    }
}
