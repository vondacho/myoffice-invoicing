package edu.noia.myoffice.invoicing.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.serializer.CommonSerializers;
import edu.noia.myoffice.invoicing.command.service.axon.AxonInvoicingService;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.sale.common.serializer.SaleSerializers;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class InvoicingCommandComponentConfig {

    @Bean
    public AxonInvoicingService invoicingCommandHandler(FolderRepository folderRepository,
                                                        DebtRepository debtRepository,
                                                        EventPublisher eventPublisher) {
        return new AxonInvoicingServiceProxy(folderRepository, debtRepository, eventPublisher);
    }

    @Bean
    public Serializer serializer() {
        return new JacksonSerializer(
                new ObjectMapper()
                        .registerModule(CommonSerializers.getModule())
                        .registerModule(SaleSerializers.getModule())
                        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY));
    }
}
