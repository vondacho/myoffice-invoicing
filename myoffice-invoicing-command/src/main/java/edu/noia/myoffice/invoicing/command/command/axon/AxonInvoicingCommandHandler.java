package edu.noia.myoffice.invoicing.command.command.axon;

import edu.noia.myoffice.invoicing.domain.command.InvoicingCommandHandler;
import edu.noia.myoffice.invoicing.domain.command.debt.PayDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.RecallDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.AskCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.CreateFolderCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.InvoiceCartCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.RegisterTicketCommand;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.axonframework.commandhandling.CommandHandler;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AxonInvoicingCommandHandler implements InvoicingCommandHandler {

    @NonNull
    InvoicingCommandHandler commandHandler;

    @CommandHandler
    @Override
    public void create(CreateFolderCommand command) {
        commandHandler.create(command);
    }

    @CommandHandler
    @Override
    public void ask(AskCommand command) {
        commandHandler.ask(command);
    }

    @CommandHandler
    @Override
    public void charge(InvoiceCartCommand command) {
        commandHandler.charge(command);
    }

    @CommandHandler
    @Override
    public void pay(PayDebtCommand command) {
        commandHandler.pay(command);
    }

    @CommandHandler
    @Override
    public void recall(RecallDebtCommand command) {
        commandHandler.recall(command);
    }

    @CommandHandler
    @Override
    public void register(RegisterTicketCommand command) {
        commandHandler.register(command);
    }
}