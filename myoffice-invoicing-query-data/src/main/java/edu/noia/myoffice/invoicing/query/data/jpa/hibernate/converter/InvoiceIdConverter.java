package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.sale.domain.vo.InvoiceId;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class InvoiceIdConverter implements AttributeConverter<InvoiceId, String> {
    @Override
    public String convertToDatabaseColumn(InvoiceId attribute) {
        return attribute != null ? attribute.getId().toString() : null;
    }

    @Override
    public InvoiceId convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? InvoiceId.of(UUID.fromString(dbData)) : null;
    }
}