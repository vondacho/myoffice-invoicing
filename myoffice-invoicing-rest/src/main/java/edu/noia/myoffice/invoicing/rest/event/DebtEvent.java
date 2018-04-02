package edu.noia.myoffice.invoicing.rest.event;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import org.springframework.hateoas.Resource;

import java.time.Instant;

public class DebtEvent extends InvoicingEvent<Resource<DebtId>> {

    public DebtEvent(Instant timestamp, Class eventClass, Resource<DebtId> payload) {
        super(timestamp, eventClass, payload);
    }
}
