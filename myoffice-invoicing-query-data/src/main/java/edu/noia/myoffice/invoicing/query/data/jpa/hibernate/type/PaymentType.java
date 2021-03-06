package edu.noia.myoffice.invoicing.query.data.jpa.hibernate.type;

import edu.noia.myoffice.common.data.jpa.hibernate.type.AbstractUserType;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import edu.noia.myoffice.invoicing.domain.vo.Ticket;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class PaymentType extends AbstractUserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{
                LongType.INSTANCE.sqlType(), // amount in centimes
                DateType.INSTANCE.sqlType(), // date
                StringType.INSTANCE.sqlType(), // ticket id
        };
    }

    @Override
    public Class returnedClass() {
        return Payment.class;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws SQLException {
        final Optional<Amount> amount = Optional.ofNullable(rs.getLong(names[0])).map(Amount::ofCentimes);
        final Optional<LocalDate> date = Optional.ofNullable(rs.getDate(names[1])).map(Date::toLocalDate);
        final Optional<String> ticketId = Optional.ofNullable(rs.getString(names[2]));
        return amount.flatMap(a -> date.map(d -> ticketId.map(t -> Payment.of(a, d, Ticket.of(t))).orElse(Payment.of(a, d)))).orElse(null);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws SQLException {
        Payment payment = (Payment) value;
        st.setLong(index++, payment.getAmount().toCentimes());
        st.setDate(index++, Date.valueOf(payment.getDate()));
        if (payment.getTicket() != null) {
            st.setString(index, payment.getTicket().getId());
        } else {
            st.setNull(index, StringType.INSTANCE.sqlType());
        }
    }
}
