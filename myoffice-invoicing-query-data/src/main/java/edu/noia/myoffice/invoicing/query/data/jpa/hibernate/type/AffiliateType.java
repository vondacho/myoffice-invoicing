package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.type;

import edu.noia.myoffice.common.data.jpa.hibernate.type.AbstractUserType;
import edu.noia.myoffice.invoicing.domain.vo.Affiliate;
import edu.noia.myoffice.invoicing.domain.vo.CustomerId;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.StringType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class AffiliateType extends AbstractUserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{
                StringType.INSTANCE.sqlType(),
                BooleanType.INSTANCE.sqlType(),
        };
    }

    @Override
    public Class returnedClass() {
        return Affiliate.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final Optional<String> uuid = Optional.ofNullable(rs.getString(names[0]));
        final Optional<Boolean> primaryDebtor = Optional.ofNullable(rs.getBoolean(names[1]));
        return uuid.flatMap(u -> primaryDebtor.map(p -> Affiliate.of(CustomerId.of(UUID.fromString(u)), p))).orElse(null);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        Affiliate affiliate = (Affiliate) value;
        st.setString(index++, affiliate.getCustomerId().getId().toString());
        st.setBoolean(index, affiliate.getPrimaryDebtor());
    }
}
