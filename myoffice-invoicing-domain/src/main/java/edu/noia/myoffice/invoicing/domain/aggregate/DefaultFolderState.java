package edu.noia.myoffice.invoicing.domain.aggregate;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.vo.Affiliate;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;

import static edu.noia.myoffice.common.util.exception.ExceptionSuppliers.notFound;

public interface DefaultFolderState extends FolderState {

    default FolderState ask(Amount amount) {
        getAskedAmount().plus(amount);
        return this;
    }

    default FolderState charge(Amount amount) {
        getDebtAmount().plus(amount);
        return this;
    }

    default FolderState pay(Amount amount) {
        getPayedAmount().plus(amount);
        return this;
    }

    default FolderState provision(Amount amount) {
        getProvisionedAmount().plus(amount);
        return this;
    }

    default FolderState consume(Amount amount) {
        getProvisionedAmount().minus(amount);
        return this;
    }

    default FolderState addTicket(Ticket ticket) {
        getTickets().add(ticket);
        return this;
    }

    default FolderState removeTicket(Ticket ticket) {
        if (!getTickets().remove(ticket)) {
            throw notFound(Ticket.class, ticket.getId()).get();
        }
        return this;
    }

    default FolderState addAffiliate(Affiliate affiliate) {
        getAffiliates().add(affiliate);
        return this;
    }

    default FolderState removeAffiliate(Affiliate affiliate) {
        if (!getAffiliates().remove(affiliate)) {
            throw notFound(Affiliate.class, affiliate.getCustomerId()).get();
        }
        return this;
    }

}
