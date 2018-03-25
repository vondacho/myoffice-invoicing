package edu.noia.myoffice.sale.rest.endpoint;

import edu.noia.myoffice.sale.rest.event.SaleEvent;
import edu.noia.myoffice.sale.rest.handler.SaleEventBroker;
import edu.noia.myoffice.sale.rest.handler.SaleEventFluxSinkRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/sale/v1/events")
public class SaleEventEndpoint {

    @Autowired
    SaleEventBroker saleEventBroker;

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<SaleEvent> saleEvents() {
        return Flux.create(sink -> SaleEventFluxSinkRegistrar.register(sink, saleEventBroker));
    }
}
