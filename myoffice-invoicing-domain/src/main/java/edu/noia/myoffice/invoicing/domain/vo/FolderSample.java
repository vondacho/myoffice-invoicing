package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.aggregate.DefaultFolderState;
import edu.noia.myoffice.invoicing.domain.aggregate.FolderState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderSample implements DefaultFolderState {

    @Setter(value = AccessLevel.PRIVATE)
    Amount debtAmount = Amount.from(Amount.ZERO);
    @Setter(value = AccessLevel.PRIVATE)
    Amount provisionedAmount = Amount.from(Amount.ZERO);
    @Setter(value = AccessLevel.PRIVATE)
    Amount payedAmount = Amount.from(Amount.ZERO);
    @Setter(value = AccessLevel.PRIVATE)
    Amount askedAmount = Amount.from(Amount.ZERO);

    @Setter(value = AccessLevel.PRIVATE)
    Set<Affiliate> affiliates = new HashSet<>();
    @Setter(value = AccessLevel.PRIVATE)
    Set<Ticket> tickets = new HashSet<>();

    public static FolderSample from(FolderState state) {
        return new FolderSample()
                .setAskedAmount(Amount.from(state.getAskedAmount()))
                .setDebtAmount(Amount.from(state.getDebtAmount()))
                .setPayedAmount(Amount.from(state.getPayedAmount()))
                .setProvisionedAmount(Amount.from(state.getProvisionedAmount()))
                .setTickets(new HashSet<>(state.getTickets()))
                .setAffiliates(new HashSet<>(state.getAffiliates()));
    }
}
