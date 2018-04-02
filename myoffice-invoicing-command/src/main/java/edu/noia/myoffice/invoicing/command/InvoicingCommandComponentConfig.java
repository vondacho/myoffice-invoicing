package edu.noia.myoffice.invoicing.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.serializer.CommonSerializers;
import edu.noia.myoffice.invoicing.command.handler.axon.AxonInvoicingService;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.DefaultValues;
import edu.noia.myoffice.sale.common.serializer.InvoicingSerializers;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
public class InvoicingCommandComponentConfig {

    @Bean
    public AxonInvoicingService invoicingCommandHandler(DefaultValues defaultValues,
                                                        FolderRepository folderRepository,
                                                        DebtRepository debtRepository,
                                                        EventPublisher eventPublisher) {
        return new AxonInvoicingService(defaultValues, folderRepository, debtRepository, eventPublisher);
    }

    @Bean
    public Serializer serializer() {
        return new JacksonSerializer(
                new ObjectMapper()
                        .registerModule(CommonSerializers.getModule())
                        .registerModule(InvoicingSerializers.getModule())
                        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY));
    }
}
