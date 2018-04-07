package edu.noia.myoffice.invoicing;

import edu.noia.myoffice.invoicing.command.InvoicingCommandComponentConfig;
import edu.noia.myoffice.invoicing.command.data.InvoicingCommandDataComponentConfig;
import edu.noia.myoffice.invoicing.config.DefaultValuesProperties;
import edu.noia.myoffice.invoicing.messaging.InvoicingMessagingComponentConfig;
import edu.noia.myoffice.invoicing.query.data.InvoicingQueryDataComponentConfig;
import edu.noia.myoffice.invoicing.rest.InvoicingRestComponentConfig;
import edu.obya.myoffice.invoicing.query.InvoicingQueryComponentConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(value = {
        InvoicingCommandComponentConfig.class,
        InvoicingCommandDataComponentConfig.class,
        InvoicingQueryComponentConfig.class,
        InvoicingQueryDataComponentConfig.class,
        InvoicingMessagingComponentConfig.class,
        InvoicingRestComponentConfig.class
})
@EnableConfigurationProperties(DefaultValuesProperties.class)
@Configuration
public class InvoicingApplicationConfig {
}
