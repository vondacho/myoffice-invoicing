package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class FolderIdConverter implements AttributeConverter<FolderId, String> {
    @Override
    public String convertToDatabaseColumn(FolderId attribute) {
        return attribute != null ? attribute.getId().toString() : null;
    }

    @Override
    public FolderId convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? FolderId.of(UUID.fromString(dbData)) : null;
    }
}