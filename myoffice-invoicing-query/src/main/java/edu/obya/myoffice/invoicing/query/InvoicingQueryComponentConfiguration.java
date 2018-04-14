package edu.obya.myoffice.invoicing.query;

import edu.obya.myoffice.invoicing.query.event.axon.AxonDebtEventHandler;
import edu.obya.myoffice.invoicing.query.event.axon.AxonFolderEventHandler;
import edu.obya.myoffice.invoicing.query.handler.DebtUpdater;
import edu.obya.myoffice.invoicing.query.handler.FolderUpdater;
import edu.obya.myoffice.invoicing.query.repository.DebtStateRepository;
import edu.obya.myoffice.invoicing.query.repository.FolderStateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoicingQueryComponentConfiguration {

    @Bean
    public AxonFolderEventHandler folderUpdater(FolderStateRepository folderStateRepository) {
        return new AxonFolderEventHandler(new FolderUpdater(folderStateRepository));
    }

    @Bean
    public AxonDebtEventHandler debtUpdater(DebtStateRepository debtStateRepository) {
        return new AxonDebtEventHandler(new DebtUpdater(debtStateRepository));
    }
}
