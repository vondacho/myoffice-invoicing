package edu.noia.myoffice.sale.rest.handler;

import edu.noia.myoffice.common.util.processor.Processor;
import edu.noia.myoffice.common.util.processor.Processors;
import edu.noia.myoffice.sale.rest.event.SaleEvent;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

@Slf4j
public class SaleEventFluxSinkRegistrar {

    private SaleEventFluxSinkRegistrar() {
    }

    public static void register(FluxSink<SaleEvent> fluxSink, SaleEventBroker broker) {
        register(fluxSink, Processors.noProcessing, broker);
    }

    public static void register(FluxSink<SaleEvent> fluxSink,
                                Processor<SaleEvent, SaleEvent> processor,
                                SaleEventBroker broker) {

        UUID subscriberId = UUID.randomUUID();
        broker.subscribe(subscriberId, new SaleEventFluxSinkSubscriber(subscriberId, processor, fluxSink));

        fluxSink.onCancel(() -> {
            LOG.debug("flux {} ({}) has been cancelled", fluxSink, subscriberId);
            broker.unsubscribe(subscriberId);
        });

        fluxSink.onDispose(() -> {
            LOG.debug("flux {} ({}) has been disposed", fluxSink, subscriberId);
            broker.unsubscribe(subscriberId);
        });
    }
}