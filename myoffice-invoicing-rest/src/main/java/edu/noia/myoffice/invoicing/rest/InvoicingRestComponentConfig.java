package edu.noia.myoffice.invoicing.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import edu.noia.myoffice.common.serializer.CommonSerializers;
import edu.noia.myoffice.invoicing.rest.handler.axon.AxonInvoicingEventBrokerProxy;
import edu.noia.myoffice.sale.common.serializer.InvoicingSerializers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@ComponentScan
@Configuration
public class InvoicingRestComponentConfig {

    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .serializationInclusion(JsonInclude.Include.NON_EMPTY)
                .modules(CommonSerializers.getModule(), InvoicingSerializers.getModule());
    }

    @Bean
    public AxonInvoicingEventBrokerProxy saleEventSource() {
        return new AxonInvoicingEventBrokerProxy();
    }
}
