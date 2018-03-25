package edu.noia.myoffice.sale.messaging.adapter.axon;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.invoicing.domain.exception.DomainExceptionHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;

@Slf4j
@RequiredArgsConstructor
public class InvoicingCommandCallback implements CommandCallback<Command, Object> {

    @NonNull
    DomainExceptionHandler exceptionHandler;

    @Override
    public void onSuccess(CommandMessage message, Object result) {
        LOG.debug("Command executed successfully: {}", message.getCommandName());
    }

    @Override
    public void onFailure(CommandMessage<? extends Command> message, Throwable cause) {
        LOG.warn("Command resulted in exception: {}", message.getCommandName());
        exceptionHandler.handle(message.getPayload(), cause);
    }
}
