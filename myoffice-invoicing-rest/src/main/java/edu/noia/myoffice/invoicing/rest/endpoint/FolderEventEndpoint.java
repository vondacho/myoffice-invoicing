package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
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

import static edu.noia.myoffice.invoicing.rest.processor.Processors.forFolder;

@Slf4j
@RestController
@RequestMapping("/api/invoicing/v1/folders/{id}/events")
public class FolderEventEndpoint {

    @Autowired
    InvoicingEventBroker invoicingEventBroker;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<InvoicingEvent> getEventFlux(@PathVariable("id") FolderId folderId) {
        return Flux.create(sink -> InvoicingEventFluxSinkRegistrar.register(sink,
                event -> forFolder(folderId).apply(event), invoicingEventBroker));
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(FolderId.class,
                new IdentifiantPropertyEditorSupport<>(s -> FolderId.of(UUID.fromString(s))));
    }
}
