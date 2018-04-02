package edu.noia.myoffice.sale.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.noia.myoffice.invoicing.domain.vo.CartId;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class InvoicingSerializers {

    private InvoicingSerializers() {
    }

    public static Module getModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CartId.class, new CartIdDeserializer());
        module.addDeserializer(FolderId.class, new FolderIdDeserializer());
        module.addDeserializer(DebtId.class, new DebtIdDeserializer());
        module.addSerializer(CartId.class, new CartIdSerializer());
        module.addSerializer(FolderId.class, new FolderIdSerializer());
        module.addSerializer(DebtId.class, new DebtIdSerializer());
        return module;
    }

    public static class CartIdSerializer extends JsonSerializer<CartId> {
        @Override
        public void serialize(CartId cartId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (cartId != null) {
                gen.writeString(cartId.getId().toString());
            }
        }
    }

    public static class CartIdDeserializer extends JsonDeserializer<CartId> {
        @Override
        public CartId deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
            return Optional.ofNullable(parser.readValueAs(String.class))
                    .filter(StringUtils::hasText)
                    .map(UUID::fromString)
                    .map(CartId::of)
                    .orElse(null);
        }
    }

    public static class FolderIdSerializer extends JsonSerializer<FolderId> {
        @Override
        public void serialize(FolderId folderId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (folderId != null) {
                gen.writeString(folderId.getId().toString());
            }
        }
    }

    public static class FolderIdDeserializer extends JsonDeserializer<FolderId> {
        @Override
        public FolderId deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
            return Optional.ofNullable(parser.readValueAs(String.class))
                    .filter(StringUtils::hasText)
                    .map(UUID::fromString)
                    .map(FolderId::of)
                    .orElse(null);
        }
    }


    public static class DebtIdSerializer extends JsonSerializer<DebtId> {
        @Override
        public void serialize(DebtId debtId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (debtId != null) {
                gen.writeString(debtId.getId().toString());
            }
        }
    }

    public static class DebtIdDeserializer extends JsonDeserializer<DebtId> {
        @Override
        public DebtId deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
            return Optional.ofNullable(parser.readValueAs(String.class))
                    .filter(StringUtils::hasText)
                    .map(UUID::fromString)
                    .map(DebtId::of)
                    .orElse(null);
        }
    }
}
