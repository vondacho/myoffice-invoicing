package edu.noia.myoffice.invoicing.query.data.jpa;

import edu.noia.myoffice.common.data.jpa.JpaBaseEntity;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.aggregate.DefaultFolderState;
import edu.noia.myoffice.invoicing.domain.aggregate.FolderState;
import edu.noia.myoffice.invoicing.domain.vo.Affiliate;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "folder_state")
@Audited
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JpaFolderState extends JpaBaseEntity implements DefaultFolderState {

    FolderId id;
    Amount debtAmount;
    Amount provisionedAmount;
    Amount payedAmount;
    Amount askedAmount;

    @Type(type = "edu.noia.myoffice.invoicing.query.data.jpa.hibernate.type.AffiliateType")
    @Columns(columns = {
            @Column(name = "customerId"),
            @Column(name = "primaryDebtor")
    })
    @ElementCollection
    @CollectionTable(name = "folder_affiliate", joinColumns = @JoinColumn(name = "fk_folder"))
    Set<Affiliate> affiliates = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "folder_ticket", joinColumns = @JoinColumn(name = "fk_folder"))
    Set<Ticket> tickets = new HashSet<>();

    public static JpaFolderState of(FolderId folderId, FolderState state) {
        return new JpaFolderState()
                .setId(folderId)
                .setAskedAmount(state.getAskedAmount())
                .setDebtAmount(state.getDebtAmount())
                .setPayedAmount(state.getPayedAmount())
                .setProvisionedAmount(state.getProvisionedAmount())
                .setAffiliates(state.getAffiliates())
                .setTickets(state.getTickets());
    }
}
