package edu.noia.myoffice.invoicing.command.handler.axon;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.command.debt.PayDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.RecallDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.AskCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.CreateFolderCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.InvoiceCartCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.RegisterTicketCommand;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.service.InvoicingService;
import edu.noia.myoffice.invoicing.domain.vo.DefaultValues;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AxonInvoicingService extends InvoicingService {

    public AxonInvoicingService(DefaultValues defaultValues,
                                FolderRepository folderRepository,
                                DebtRepository debtRepository,
                                EventPublisher eventPublisher) {
        super(defaultValues, folderRepository, debtRepository, eventPublisher);
    }

    @CommandHandler
    @Override
    public void create(CreateFolderCommand command) {
        folderRepository.save(command.getFolderId(), null);
    }

    @Override
    protected Holder<Debt> create(DebtState state, Consumer<EventPayload> eventConsumer) {
        return debtRepository.save(null, state);
    }

    @CommandHandler
    @Override
    public void ask(AskCommand command) {
        super.ask(command);
    }

    @CommandHandler
    @Override
    public void charge(InvoiceCartCommand command) {
        super.charge(command);
    }

    @CommandHandler
    @Override
    public void pay(PayDebtCommand command) {
        super.pay(command);
    }

    @CommandHandler
    @Override
    public void recall(RecallDebtCommand command) {
        super.recall(command);
    }

    @CommandHandler
    @Override
    public void register(RegisterTicketCommand command) {
        super.register(command);
    }
}