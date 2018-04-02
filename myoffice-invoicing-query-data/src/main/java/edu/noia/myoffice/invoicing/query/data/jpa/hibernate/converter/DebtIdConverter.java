package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class DebtIdConverter implements AttributeConverter<DebtId, String> {
    @Override
    public String convertToDatabaseColumn(DebtId attribute) {
        return attribute != null ? attribute.getId().toString() : null;
    }

    @Override
    public DebtId convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? DebtId.of(UUID.fromString(dbData)) : null;
    }
}