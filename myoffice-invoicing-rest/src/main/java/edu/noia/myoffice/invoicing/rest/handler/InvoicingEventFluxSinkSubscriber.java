package edu.noia.myoffice.invoicing.rest.handler;

import edu.noia.myoffice.common.util.broker.DefaultBroker;
import edu.noia.myoffice.common.util.processor.Processor;
import edu.noia.myoffice.invoicing.rest.event.InvoicingEvent;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoicingEventFluxSinkSubscriber extends DefaultBroker.Subscriber<InvoicingEvent, InvoicingEvent> {

    FluxSink<InvoicingEvent> fluxSink;

    public InvoicingEventFluxSinkSubscriber(
            UUID id,
            Processor<InvoicingEvent, InvoicingEvent> processor,
            FluxSink<InvoicingEvent> fluxSink) {

        super(event -> {
            if (!fluxSink.isCancelled()) {
                fluxSink.next(event);
            } else {
                LOG.debug("flux {} ({}) is already cancelled", fluxSink, id);
            }
        }, processor);

        this.fluxSink = fluxSink;
    }

    @Override
    public void complete() {
        if (!fluxSink.isCancelled()) {
            fluxSink.complete();
            LOG.debug("flux {} has been completed", fluxSink);
        } else {
            LOG.debug("flux {} is already cancelled", fluxSink);
        }
    }
}
