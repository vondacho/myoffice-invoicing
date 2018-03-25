package edu.noia.myoffice.invoicing.command.service.axon;

import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.service.InvoicingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AxonInvoicingService extends InvoicingService {

    public AxonInvoicingService(FolderRepository folderRepository,
                                DebtRepository debtRepository,
                                EventPublisher eventPublisher) {
        super(folderRepository, debtRepository, eventPublisher);
    }
}
