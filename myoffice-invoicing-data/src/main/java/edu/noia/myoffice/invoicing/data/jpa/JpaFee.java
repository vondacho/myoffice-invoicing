package edu.noia.myoffice.invoicing.data.jpa;

import edu.noia.myoffice.common.data.jpa.JpaAuditableEntity;
import edu.noia.myoffice.invoicing.domain.vo.FeeId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Accessors(chain=true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JpaFee extends JpaAuditableEntity {

    FeeId id;
    FolderId folderId;
    String title;
    BigDecimal quantity;
    BigDecimal tariff;
    BigDecimal price;
}
