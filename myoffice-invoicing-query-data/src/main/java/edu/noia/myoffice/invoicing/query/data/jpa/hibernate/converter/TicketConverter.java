package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TicketConverter implements AttributeConverter<Ticket, String> {
    @Override
    public String convertToDatabaseColumn(Ticket attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public Ticket convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? Ticket.of(dbData) : null;
    }
}