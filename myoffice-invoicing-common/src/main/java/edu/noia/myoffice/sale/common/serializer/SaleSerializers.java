package edu.noia.myoffice.sale.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import edu.noia.myoffice.common.serializer.CommonSerializers;
import edu.noia.myoffice.sale.domain.vo.ArticleId;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.domain.vo.FolderId;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static edu.noia.myoffice.common.util.converter.Converters.toLocalDateTimeUTC;

public class SaleSerializers {

    private SaleSerializers() {
    }

    public static Module getModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(CartId.class, new CartIdDeserializer());
        module.addDeserializer(FolderId.class, new FolderIdDeserializer());
        module.addDeserializer(ArticleId.class, new ArticleIdDeserializer());
        module.addSerializer(CartId.class, new CartIdSerializer());
        module.addSerializer(FolderId.class, new FolderIdSerializer());
        module.addSerializer(ArticleId.class, new ArticleIdSerializer());
        module.addSerializer(Instant.class, new InstantSerializer());
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

    public static class ArticleIdSerializer extends JsonSerializer<ArticleId> {
        @Override
        public void serialize(ArticleId articleId, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (articleId != null) {
                gen.writeString(articleId.getId().toString());
            }
        }
    }

    public static class ArticleIdDeserializer extends JsonDeserializer<ArticleId> {
        @Override
        public ArticleId deserialize(JsonParser parser, DeserializationContext ctx) throws IOException {
            return Optional.ofNullable(parser.readValueAs(String.class))
                    .filter(StringUtils::hasText)
                    .map(UUID::fromString)
                    .map(ArticleId::of)
                    .orElse(null);
        }
    }

    public static class InstantSerializer extends JsonSerializer<Instant> {

        final JsonSerializer<LocalDateTime> localDateTimeSerializer = new CommonSerializers.LocalDateTimeSerializer();

        @Override
        public void serialize(Instant instant, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            localDateTimeSerializer.serialize(toLocalDateTimeUTC(instant), gen, serializers);
        }
    }
}
