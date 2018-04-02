package edu.noia.myoffice.invoicing.rest.handler;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.broker.DefaultBroker;
import edu.noia.myoffice.invoicing.domain.event.debt.DebtEventPayload;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
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
    ResourceProcessor<Resource<DebtId>> hateoasProcessor;

    public void onSuccess(DebtEventPayload event, Instant timestamp) {
        toCartEvent(timestamp)
                .apply(event)
                .flatMap(addHateoas(hateoasProcessor)).ifPresent(this::accept);
    }

    public void onFailure(ProblemEventPayload event, Instant timestamp) {
        toProblemEvent(timestamp).apply(event).ifPresent(this::accept);
    }
}
