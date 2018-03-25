package edu.noia.myoffice.invoicing.data.jpa;

import edu.noia.myoffice.common.data.jpa.JpaAuditableEntity;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@Accessors(chain=true)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JpaTicket extends JpaAuditableEntity {

    FolderId folderId;
    String id;
}
