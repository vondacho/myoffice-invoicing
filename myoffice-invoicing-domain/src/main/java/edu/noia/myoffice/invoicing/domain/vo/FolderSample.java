package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.MutableAmount;
import edu.noia.myoffice.invoicing.domain.aggregate.FolderState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static java.util.Collections.emptyList;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderSample implements FolderState {

    MutableAmount debtAmount = new MutableAmount(Amount.ZERO);
    MutableAmount provisionedAmount = new MutableAmount(Amount.ZERO);
    MutableAmount payedAmount = new MutableAmount(Amount.ZERO);
    MutableAmount askedAmount = new MutableAmount(Amount.ZERO);

    public static FolderSample from(FolderState state) {
        return new FolderSample();
    }

    @Override
    public void ask(Amount amount) {
        askedAmount.plus(amount);
    }

    @Override
    public void charge(Amount amount) {
        debtAmount.plus(amount);
    }

    @Override
    public void pay(Amount amount) {
        payedAmount.plus(amount);
    }

    @Override
    public void provision(Amount amount) {
        provisionedAmount.plus(amount);
    }

    @Override
    public void consume(Amount amount) {
        provisionedAmount.minus(amount);
    }

    @Override
    public FolderState affiliate(CustomerId customerId) {
        return this;
    }

    @Override
    public FolderState addTicket(Ticket ticket) {
        return this;
    }

    @Override
    public List<CustomerId> getDebtors() {
        return emptyList();
    }

    @Override
    public List<Ticket> getTickets() {
        return emptyList();
    }
}
