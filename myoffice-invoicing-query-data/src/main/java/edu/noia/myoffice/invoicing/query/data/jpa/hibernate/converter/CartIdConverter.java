package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.sale.domain.vo.CartId;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class CartIdConverter implements AttributeConverter<CartId, String> {
    @Override
    public String convertToDatabaseColumn(CartId attribute) {
        return attribute != null ? attribute.getId().toString() : null;
    }

    @Override
    public CartId convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? CartId.of(UUID.fromString(dbData)) : null;
    }
}