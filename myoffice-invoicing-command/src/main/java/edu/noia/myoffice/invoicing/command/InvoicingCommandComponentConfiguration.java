package edu.noia.myoffice.invoicing.command;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.vo.Quantity;
import edu.noia.myoffice.common.mixin.QuantityMixin;
import edu.noia.myoffice.common.serializer.CommonSerializers;
import edu.noia.myoffice.invoicing.command.command.axon.AxonInvoicingCommandHandler;
import edu.noia.myoffice.invoicing.command.service.axon.AxonInvoicingService;
import edu.noia.myoffice.invoicing.domain.command.InvoicingCommandHandler;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.DefaultValues;
import edu.noia.myoffice.sale.common.serializer.InvoicingSerializers;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@ComponentScan
@Configuration
public class InvoicingCommandComponentConfiguration {

    @Bean
    public InvoicingCommandHandler invoicingService(DefaultValues defaultValues,
                                                    FolderRepository folderRepository,
                                                    DebtRepository debtRepository,
                                                    EventPublisher eventPublisher) {
        return new AxonInvoicingCommandHandler(
                new AxonInvoicingService(defaultValues, folderRepository, debtRepository, eventPublisher));
    }

    @Primary
    @Bean
    public Serializer serializer() {
        return new JacksonSerializer(
                new ObjectMapper()
                        .registerModule(CommonSerializers.getModule())
                        .registerModule(InvoicingSerializers.getModule())
                        .addMixIn(Quantity.class, QuantityMixin.class)
                        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY));
    }
}
