package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.entity.EntityState;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.vo.Affiliate;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;

import java.util.Set;

public interface FolderState extends EntityState {

    Amount getAskedAmount();

    Amount getDebtAmount();

    Amount getProvisionedAmount();

    Amount getPayedAmount();

    Set<Affiliate> getAffiliates();

    Set<Ticket> getTickets();

    FolderState ask(Amount amount);

    FolderState charge(Amount amount);

    FolderState pay(Amount amount);

    FolderState provision(Amount amount);

    FolderState consume(Amount amount);

    FolderState addTicket(Ticket ticket);

    FolderState addAffiliate(Affiliate affiliate);
}
