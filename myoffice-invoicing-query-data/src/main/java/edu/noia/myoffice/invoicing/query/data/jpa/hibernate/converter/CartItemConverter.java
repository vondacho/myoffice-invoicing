package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.converter;

import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.Tariff;
import edu.noia.myoffice.common.domain.vo.Unit;
import edu.noia.myoffice.sale.domain.vo.Article;
import edu.noia.myoffice.sale.domain.vo.ArticleId;
import edu.noia.myoffice.sale.domain.vo.CartItem;
import edu.noia.myoffice.sale.domain.vo.CartItemId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class CartItemConverter implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{
                StringType.INSTANCE.sqlType(), // id
                LongType.INSTANCE.sqlType(), // quantity in tariff unit
                StringType.INSTANCE.sqlType(), // article id
                StringType.INSTANCE.sqlType(), // article name
                LongType.INSTANCE.sqlType(), // tariff amount in centimes
                StringType.INSTANCE.sqlType(), // tariff unit
                DateType.INSTANCE.sqlType(), // timestamp
        };
    }

    @Override
    public Class returnedClass() {
        return CartItem.class;
    }

    @Override
    public boolean equals(Object x, Object y) {
        return x != null && x.equals(y);
    }

    @Override
    public int hashCode(Object x) {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        try {
            return CartItem.of(
                    CartItemId.of(UUID.fromString(rs.getString(names[0]))),
                    rs.getLong(names[1]),
                    Article.of(
                            ArticleId.of(UUID.fromString(rs.getString(names[2]))),
                            rs.getString(names[3]),
                            Tariff.of(
                                    Amount.ofCentimes(rs.getLong(names[4])),
                                    Unit.valueOf(rs.getString(names[5])))),
                    rs.getTimestamp(names[6]) != null ? rs.getTimestamp(names[6]).toLocalDateTime() : null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        CartItem cartItem = (CartItem) value;
        // item id
        st.setString(index++, cartItem.getId().getId().toString());
        // item quantity
        st.setLong(index++, cartItem.getQuantity());
        // article id
        st.setString(index++, cartItem.getArticle().getArticleId().getId().toString());
        // article name
        st.setString(index++, cartItem.getArticle().getName());
        // tariff price
        st.setLong(index++, cartItem.getArticle().getTariff().getValue().toCentimes());
        // tariff unit
        st.setString(index++, cartItem.getArticle().getTariff().getUnit().toString());
        // item timestamp
        // FIXME the absence of value for cohabitation with Envers
        st.setTimestamp(index, cartItem.getTimestamp() != null ? Timestamp.valueOf(cartItem.getTimestamp()) : Timestamp.from(Instant.now()));
    }

    @Override
    public Object deepCopy(Object value) {
        return value;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object value) {
        return (Serializable) value;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) {
        return original;
    }
}
