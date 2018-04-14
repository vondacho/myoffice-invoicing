package edu.noia.myoffice.invoicing.config;

import edu.noia.myoffice.invoicing.command.InvoicingCommandComponentConfiguration;
import edu.noia.myoffice.invoicing.command.data.InvoicingCommandDataComponentConfiguration;
import edu.noia.myoffice.invoicing.messaging.InvoicingMessagingComponentConfiguration;
import edu.noia.myoffice.invoicing.query.data.InvoicingQueryDataComponentConfiguration;
import edu.noia.myoffice.invoicing.rest.InvoicingRestComponentConfiguration;
import edu.obya.myoffice.invoicing.query.InvoicingQueryComponentConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
        InvoicingCommandComponentConfiguration.class,
        InvoicingCommandDataComponentConfiguration.class,
        InvoicingQueryComponentConfiguration.class,
        InvoicingQueryDataComponentConfiguration.class,
        InvoicingMessagingComponentConfiguration.class,
        InvoicingRestComponentConfiguration.class
})
@EnableConfigurationProperties(DefaultValuesProperties.class)
@Configuration
public class InvoicingApplicationConfiguration {
}
