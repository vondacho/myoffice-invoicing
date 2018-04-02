package edu.noia.myoffice.invoicing.messaging.adapter.axon;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.common.domain.command.CommandPublisher;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;

import static org.axonframework.commandhandling.GenericCommandMessage.asCommandMessage;

@RequiredArgsConstructor
public class CommandPublisherAdapter implements CommandPublisher {

    @NonNull
    CommandGateway commandGateway;
    @NonNull
    CommandCallback saleCommandCallback;

    @Override
    public void accept(Command command) {
        commandGateway.send(asCommandMessage(command), saleCommandCallback);
    }
}
