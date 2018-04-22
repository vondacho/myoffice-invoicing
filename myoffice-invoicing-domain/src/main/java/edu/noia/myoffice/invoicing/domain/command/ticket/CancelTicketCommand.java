package edu.noia.myoffice.invoicing.domain.command.ticket;

import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelTicketCommand implements TicketCommand {
    @NonNull
    Ticket ticket;
}
