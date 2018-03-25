package edu.noia.myoffice.sale.messaging;

import edu.noia.myoffice.common.domain.command.CommandPublisher;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.sale.domain.exception.DomainExceptionHandler;
import edu.noia.myoffice.sale.messaging.adapter.axon.CommandPublisherAdapter;
import edu.noia.myoffice.sale.messaging.adapter.axon.EventPublisherAdapter;
import edu.noia.myoffice.sale.messaging.adapter.axon.InvoicingCommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoicingMessagingComponentConfig {

    @Bean
    public EventPublisher eventPublisher(EventBus eventBus) {
        return new EventPublisherAdapter(eventBus);
    }

    @Bean
    public CommandPublisher commandPublisher(CommandGateway commandGateway, InvoicingCommandCallback commandCallback) {
        return new CommandPublisherAdapter(commandGateway, commandCallback);
    }

    @Bean
    public InvoicingCommandCallback exceptionHandlerCallback(EventPublisher eventPublisher) {
        return new InvoicingCommandCallback(new DomainExceptionHandler(eventPublisher));
    }
}
