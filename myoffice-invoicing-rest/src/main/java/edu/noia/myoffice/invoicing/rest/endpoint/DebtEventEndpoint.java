package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
import edu.noia.myoffice.invoicing.rest.handler.InvoicingEventBroker;
import edu.noia.myoffice.invoicing.rest.handler.InvoicingEventFluxSinkRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

import static edu.noia.myoffice.invoicing.rest.processor.Processors.forCart;

@Slf4j
@RestController
@RequestMapping("/api/invoicing/v1/debts/{id}/events")
public class DebtEventEndpoint {

    @Autowired
    InvoicingEventBroker invoicingEventBroker;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<InvoicingEvent> cartEvents(@PathVariable("id") DebtId debtId) {
        return Flux.create(sink -> InvoicingEventFluxSinkRegistrar.register(sink,
                event -> forCart(debtId).apply(event), invoicingEventBroker));
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(DebtId.class,
                new IdentifiantPropertyEditorSupport<>(s -> DebtId.of(UUID.fromString(s))));
    }
}
