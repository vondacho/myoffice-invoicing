package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
import edu.noia.myoffice.invoicing.rest.handler.InvoicingEventBroker;
import edu.noia.myoffice.invoicing.rest.handler.InvoicingEventFluxSinkRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/invoicing/v1/events")
public class InvoicingEventEndpoint {

    @Autowired
    InvoicingEventBroker invoicingEventBroker;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<InvoicingEvent> saleEvents() {
        return Flux.create(sink -> InvoicingEventFluxSinkRegistrar.register(sink, invoicingEventBroker));
    }
}
