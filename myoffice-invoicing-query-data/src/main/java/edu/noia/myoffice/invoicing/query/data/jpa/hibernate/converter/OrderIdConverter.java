package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.sale.domain.vo.OrderId;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class OrderIdConverter implements AttributeConverter<OrderId, String> {
    @Override
    public String convertToDatabaseColumn(OrderId attribute) {
        return attribute != null ? attribute.getId().toString() : null;
    }

    @Override
    public OrderId convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? OrderId.of(UUID.fromString(dbData)) : null;
    }
}