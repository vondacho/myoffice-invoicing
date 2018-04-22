package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.type;

import edu.noia.myoffice.common.data.jpa.hibernate.type.AbstractUserType;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.vo.Recall;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class RecallType extends AbstractUserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{
                LongType.INSTANCE.sqlType(), // amount in centimes
                DateType.INSTANCE.sqlType(), // date
        };
    }

    @Override
    public Class returnedClass() {
        return Recall.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        Optional<Amount> amount = Optional.ofNullable(rs.getLong(names[0])).map(Amount::ofCentimes);
        Optional<LocalDate> date = Optional.ofNullable(rs.getDate(names[1])).map(Date::toLocalDate);
        return amount.flatMap(a -> date.map(d -> Recall.of(a, d))).orElse(null);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        Recall recall = (Recall) value;
        st.setLong(index++, recall.getAmount().toCentimes());
        st.setDate(index, Date.valueOf(recall.getDate()));
    }
}
