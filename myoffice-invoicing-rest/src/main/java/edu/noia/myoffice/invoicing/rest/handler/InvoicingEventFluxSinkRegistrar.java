package edu.noia.myoffice.invoicing.rest.handler;

import edu.noia.myoffice.common.util.processor.Processor;
import edu.noia.myoffice.common.util.processor.Processors;
import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

@Slf4j
public class InvoicingEventFluxSinkRegistrar {

    private InvoicingEventFluxSinkRegistrar() {
    }

    public static void register(FluxSink<InvoicingEvent> fluxSink, InvoicingEventBroker broker) {
        register(fluxSink, Processors.noProcessing, broker);
    }

    public static void register(FluxSink<InvoicingEvent> fluxSink,
                                Processor<InvoicingEvent, InvoicingEvent> processor,
                                InvoicingEventBroker broker) {

        UUID subscriberId = UUID.randomUUID();
        broker.subscribe(subscriberId, new InvoicingEventFluxSinkSubscriber(subscriberId, processor, fluxSink));

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