package edu.noia.myoffice.invoicing.domain.service;

import edu.noia.myoffice.common.domain.event.EventPayload;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.util.holder.DefaultHolder;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.DebtState;
import edu.noia.myoffice.invoicing.domain.aggregate.Folder;
import edu.noia.myoffice.invoicing.domain.command.debt.PayDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.RecallDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.AskCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.CreateFolderCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.InvoiceCartCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.RegisterTicketCommand;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.function.Consumer;

import static edu.noia.myoffice.common.util.exception.ExceptionSuppliers.notFound;
import static java.time.LocalDate.now;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class InvoicingService {

    @NonNull
    DefaultValues defaultValues;
    @NonNull
    FolderRepository folderRepository;
    @NonNull
    DebtRepository debtRepository;
    @NonNull
    EventPublisher eventPublisher;

    public void create(CreateFolderCommand command) {
        Folder.create(command.getFolderId(), eventPublisher::publish).save(folderRepository);
    }

    public void ask(AskCommand command) {
        applyOn(command.getFolderId(), folder ->
                create(DebtSample.of(command.getFolderId(), command.getAmount())
                        .setDiscountRate(defaultValues.getDiscountRate())
                        .setTaxRate(defaultValues.getTaxRate())
                        .setDelayDayCount(defaultValues.getDelayDayCount())
                        .setDelayDate(now().plusDays(defaultValues.getDelayDayCount())), eventPublisher::publish)
                        .execute(debt -> {
                            folder.ask(debt.getTotal(), eventPublisher::publish);
                            debt.save(debtRepository);
                        }));
    }

    public void charge(InvoiceCartCommand command) {
        applyOn(command.getFolderId(), folder ->
                create(DebtSample.of(command.getFolderId(), command.getCartId(), command.getAmount())
                        .setDiscountRate(defaultValues.getDiscountRate())
                        .setTaxRate(defaultValues.getTaxRate())
                        .setDelayDayCount(defaultValues.getDelayDayCount())
                        .setDelayDate(now().plusDays(defaultValues.getDelayDayCount())), eventPublisher::publish)
                        .execute(debt -> {
                            folder.charge(debt.getTotal(), eventPublisher::publish);
                            debt.save(debtRepository);
                        }));
    }

    protected Holder<Debt> create(DebtState state, Consumer<EventPayload> eventConsumer) {
        return DefaultHolder.of(Debt.create(state, eventConsumer));
    }

    public void pay(PayDebtCommand command) {
        applyOn(command.getFolderId(), folder -> {
            Amount availableAmount = command.getDebts().stream()
                    .map(this::find)
                    .reduce(command.getPayment().getAmount(),
                            (available, debt) -> {
                                if (available.gt(Amount.ZERO)) {
                                    Amount remaining = Amount.from(available);
                                    debt.execute(d ->
                                            remaining.set(d.pay(Payment.of(
                                                    remaining,
                                                    command.getPayment().getDate(),
                                                    command.getPayment().getTicket()), eventPublisher::publish)));
                                    return remaining;
                                } else return Amount.ZERO;
                            },
                            (a, b) -> b);

            folder.pay(command.getPayment(), eventPublisher::publish);
            if (availableAmount.gt(Amount.ZERO)) {
                folder.provision(
                        Payment.of(availableAmount, command.getPayment().getDate(), command.getPayment().getTicket()),
                        eventPublisher::publish);
            }
        });
    }

    public void recall(RecallDebtCommand command) {
        applyOn(command.getDebtId(), debt -> debt.recall(eventPublisher::publish));
    }

    public void register(RegisterTicketCommand command) {
        applyOn(command.getFolderId(), folder -> folder.register(command.getTicket(), eventPublisher::publish));
    }

    public void applyOn(FolderId folderId, Consumer<Folder> action) {
        find(folderId).execute(action);
    }

    public void applyOn(DebtId debtId, Consumer<Debt> action) {
        find(debtId).execute(action);
    }

    private Holder<Folder> find(FolderId folderId) {
        return folderRepository.findOne(folderId).orElseThrow(notFound(Folder.class, folderId));
    }

    private Holder<Debt> find(DebtId debtId) {
        return debtRepository.findOne(debtId).orElseThrow(notFound(Debt.class, debtId));
    }
}
