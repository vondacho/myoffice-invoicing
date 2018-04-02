package edu.noia.myoffice.invoicing.messaging.adapter.axon;

import edu.noia.myoffice.common.domain.event.Event;
import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventBus;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;

@RequiredArgsConstructor
public class EventPublisherAdapter implements EventPublisher {

    @NonNull
    EventBus eventBus;

    @Override
    public void publish(Event event) {
        eventBus.publish(asEventMessage(event.getPayload()));
    }

    @Override
    public void publish(EventPayload payload) {
        eventBus.publish(asEventMessage(payload));
    }
}
