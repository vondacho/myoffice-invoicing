package edu.noia.myoffice.invoicing.query.data.jpa;

import edu.noia.myoffice.common.data.jpa.JpaBaseEntity;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.aggregate.DefaultDebtState;
import edu.noia.myoffice.invoicing.domain.vo.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Table(name = "debt_state")
@Audited
@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JpaDebtState extends JpaBaseEntity implements DefaultDebtState {

    DebtId id;
    FolderId folderId;
    CartId cartId;
    Amount cartAmount;
    Percentage discountRate;
    Percentage taxRate;
    Integer delayDayCount;
    LocalDate delayDate;
    Amount amount;
    String notes;
    Amount payedAmount;
    @Enumerated(value = EnumType.STRING)
    DebtStatus status;

    @Type(type = "edu.noia.myoffice.invoicing.data.jpa.hibernate.type.PaymentType")
    @Columns(columns = {
            @Column(name = "date"),
            @Column(name = "amount"),
            @Column(name = "amount_unit"),
            @Column(name = "ticket")
    })
    @ElementCollection
    @CollectionTable(name = "debt_payment", joinColumns = @JoinColumn(name = "fk_debt"))
    List<Payment> payments = new ArrayList<>();

    @Type(type = "edu.noia.myoffice.invoicing.data.jpa.hibernate.type.RecallType")
    @Columns(columns = {
            @Column(name = "date"),
            @Column(name = "amount"),
            @Column(name = "amount_unit")
    })
    @ElementCollection
    @CollectionTable(name = "debt_recall", joinColumns = @JoinColumn(name = "fk_debt"))
    List<Recall> recalls = new ArrayList<>();

    public static JpaDebtState of(DebtId debtId, DebtState state) {
        return new JpaDebtState()
                .setId(debtId)
                .setFolderId(state.getFolderId())
                .setCartId(state.getCartId())
                .setCartAmount(state.getCartAmount())
                .setDiscountRate(state.getDiscountRate())
                .setTaxRate(state.getTaxRate())
                .setDelayDate(state.getDelayDate())
                .setDelayDayCount(state.getDelayDayCount())
                .setAmount(state.getAmount())
                .setNotes(state.getNotes())
                .setPayedAmount(state.getPayedAmount())
                .setStatus(state.getStatus());
    }
}
