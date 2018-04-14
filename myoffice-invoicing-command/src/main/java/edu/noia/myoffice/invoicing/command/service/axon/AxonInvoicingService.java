package edu.noia.myoffice.invoicing.command.service.axon;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.command.folder.CreateFolderCommand;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.service.InvoicingService;
import edu.noia.myoffice.invoicing.domain.vo.DefaultValues;

import java.util.function.Consumer;

public class AxonInvoicingService extends InvoicingService {

    public AxonInvoicingService(
            DefaultValues defaultValues,
            FolderRepository folderRepository,
            DebtRepository debtRepository,
            EventPublisher eventPublisher) {
        
        super(defaultValues, folderRepository, debtRepository, eventPublisher);
    }

    @Override
    public void create(CreateFolderCommand command) {
        folderRepository.save(command.getFolderId(), null);
    }

    @Override
    protected Holder<Debt> create(DebtState state, Consumer<EventPayload> eventConsumer) {
        return debtRepository.save(null, state);
    }
}