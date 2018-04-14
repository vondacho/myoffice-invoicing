package edu.noia.myoffice.invoicing.command.data;

import edu.noia.myoffice.invoicing.command.aggregate.axon.AxonDebt;
import edu.noia.myoffice.invoicing.command.aggregate.axon.AxonFolder;
import edu.noia.myoffice.invoicing.command.data.repository.axon.AxonDebtRepository;
import edu.noia.myoffice.invoicing.command.data.repository.axon.AxonFolderRepository;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoicingCommandDataComponentConfiguration {

    @Autowired
    private AxonConfiguration axonConfiguration;

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(new InMemoryEventStorageEngine());
    }

    @Bean
    public SagaStore sagaStore() {
        return new InMemorySagaStore();
    }

    @Bean
    public DebtRepository debtRepository() {
        return new AxonDebtRepository(axonConfiguration.repository(AxonDebt.class));
    }

    @Bean
    public FolderRepository folderRepository() {
        return new AxonFolderRepository(axonConfiguration.repository(AxonFolder.class));
    }
}
