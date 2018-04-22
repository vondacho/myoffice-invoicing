package edu.noia.myoffice.invoicing.domain.command;

import edu.noia.myoffice.invoicing.domain.command.folder.PayDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.RecallDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.ValidateDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.AskCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.CreateFolderCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.InvoiceCartCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.RegisterTicketCommand;

public interface InvoicingCommandHandler {

    void create(CreateFolderCommand command);

    void ask(AskCommand command);

    void charge(InvoiceCartCommand command);

    void validate(ValidateDebtCommand command);

    void pay(PayDebtCommand command);

    void recall(RecallDebtCommand command);

    void register(RegisterTicketCommand command);
}
