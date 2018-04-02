package edu.obya.myoffice.invoicing.query;

import edu.obya.myoffice.invoicing.query.handler.axon.AxonDebtUpdater;
import edu.obya.myoffice.invoicing.query.handler.axon.AxonFolderUpdater;
import edu.obya.myoffice.invoicing.query.repository.DebtStateRepository;
import edu.obya.myoffice.invoicing.query.repository.FolderStateRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InvoicingQueryComponentConfig {

    @Bean
    public AxonFolderUpdater folderUpdater(FolderStateRepository folderStateRepository) {
        return new AxonFolderUpdater(folderStateRepository);
    }

    @Bean
    public AxonDebtUpdater debtUpdater(DebtStateRepository debtStateRepository) {
        return new AxonDebtUpdater(debtStateRepository);
    }
}
