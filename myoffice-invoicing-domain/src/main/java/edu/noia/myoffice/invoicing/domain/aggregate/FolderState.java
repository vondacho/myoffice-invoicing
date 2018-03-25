package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.EntityState;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.MutableAmount;
import edu.noia.myoffice.invoicing.domain.vo.CustomerId;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;

import java.util.List;

public interface FolderState extends EntityState {

    MutableAmount getDebtAmount();

    MutableAmount getProvisionedAmount();

    MutableAmount getPayedAmount();

    void ask(Amount amount);

    void pay(Amount amount);

    void charge(Amount amount);

    void provision(Amount amount);

    void consume(Amount amount);

    FolderState affiliate(CustomerId customerId);

    FolderState addTicket(Ticket ticket);

    List<CustomerId> getDebtors();

    List<Ticket> getTickets();
}
