package edu.noia.myoffice.invoicing.domain.command.ticket;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;

public interface TicketCommand extends Command {
    Ticket getTicket();
}
