package edu.noia.myoffice.invoicing;

import edu.noia.myoffice.invoicing.domain.vo.DefaultValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InvoicingApplicationIT {

    @Autowired
    DefaultValues defaultValues;

    @Test
    public void contextLoads() {
        assertThat(defaultValues).isNotNull();
        assertThat(defaultValues.getDelayDayCount()).isNotNull();
        assertThat(defaultValues.getDiscountRate()).isNotNull();
        assertThat(defaultValues.getTaxRate()).isNotNull();
    }
}
