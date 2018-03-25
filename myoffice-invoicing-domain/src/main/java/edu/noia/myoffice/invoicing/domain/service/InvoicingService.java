package edu.noia.myoffice.invoicing.domain.service;

import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.vo.Amount;
import edu.noia.myoffice.common.domain.vo.MutableAmount;
import edu.noia.myoffice.common.domain.vo.Percentage;
import edu.noia.myoffice.common.util.holder.Holder;
import edu.noia.myoffice.invoicing.domain.aggregate.Debt;
import edu.noia.myoffice.invoicing.domain.aggregate.Folder;
import edu.noia.myoffice.invoicing.domain.command.*;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import edu.noia.myoffice.invoicing.domain.repository.FolderRepository;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.domain.vo.Payment;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import static edu.noia.myoffice.common.util.exception.ExceptionSuppliers.notFound;
import static java.time.LocalDate.now;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class InvoicingService {

    private static final Percentage DEFAULT_TAX_RATE = Percentage.of(8L);
    private static final Percentage DEFAULT_DISCOUNT_RATE = Percentage.ZERO;
    private static final int DEFAULT_DELAY_DAY_COUNT = 30;

    @NonNull
    FolderRepository folderRepository;
    @NonNull
    DebtRepository debtRepository;
    @NonNull
    EventPublisher eventPublisher;

    public void create(CreateFolderCommand command) {
        LOG.debug("{} received command: {}", getClass(), command);
        Folder.of(command.getFolderId(), eventPublisher).save(folderRepository);
    }

    public void ask(CreateRequestCommand command) {
        LOG.debug("{} received command: {}", getClass(), command);
        applyOn(command.getFolderId(), folder ->
                Debt.create(DebtSample.of(command.getFolderId(), command.getAmount())
                        .setDiscountRate(DEFAULT_DISCOUNT_RATE)
                        .setTaxRate(DEFAULT_TAX_RATE)
                        .setDelayDayCount(DEFAULT_DELAY_DAY_COUNT)
                        .setDelayDate(now().plusDays(DEFAULT_DELAY_DAY_COUNT)), eventPublisher)
                        .save(debtRepository)
                        .execute(debt -> folder.ask(debt.getTotal(), eventPublisher)));
    }

    public void charge(CreateInvoiceCommand command) {
        LOG.debug("{} received command: {}", getClass(), command);
        applyOn(command.getFolderId(), folder ->
                Debt.create(DebtSample.of(command.getFolderId(), command.getCartId(), command.getCartAmount())
                        .setDiscountRate(DEFAULT_DISCOUNT_RATE)
                        .setTaxRate(DEFAULT_TAX_RATE)
                        .setDelayDayCount(DEFAULT_DELAY_DAY_COUNT)
                        .setDelayDate(now().plusDays(DEFAULT_DELAY_DAY_COUNT)), eventPublisher)
                        .save(debtRepository)
                        .execute(debt -> folder.charge(debt.getTotal(), eventPublisher)));
    }

    public void pay(PayCommand command) {
        LOG.debug("{} received command: {}", getClass(), command);
        // TODO find open debts
        applyOn(command.getFolderId(), folder -> folder.pay(command.getPayment(), eventPublisher));
    }

    public void pay(PayDebtCommand command) {
        LOG.debug("{} received command: {}", getClass(), command);
        applyOn(command.getFolderId(), folder -> {
            Amount availableAmount = command.getDebts().stream()
                    .map(this::find)
                    .reduce(command.getPayment().getAmount(),
                            (a, hd) -> {
                                MutableAmount ma = a.toMutable();
                                hd.execute(debt ->
                                        ma.set(debt.pay(Payment.of(
                                                a,
                                                command.getPayment().getDate(),
                                                command.getPayment().getTicket()), eventPublisher)));
                                return ma.toImmutable();
                            },
                            (a, b) -> b);

            folder.pay(command.getPayment(), eventPublisher);
            if (availableAmount.gt(Amount.ZERO)) {
                folder.provision(
                        Payment.of(availableAmount, command.getPayment().getDate(), command.getPayment().getTicket()),
                        eventPublisher);
            }
        });
    }

    public void register(RegisterTicketCommand command) {
        LOG.debug("{} received command: {}", getClass(), command);
        applyOn(command.getFolderId(), folder -> folder.register(command.getTicket(), eventPublisher));
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

/*
    public List<Receipt> cash(FolderId folderId, Payment payment, List<Debt> debts) {
        List<Receipt> receipts = new ArrayList<>();

        Amount balance = Amount.of(payment.getAmount());

        for (Debt debt: debts) {
            receipts.add(Receipt.of(balance, payment.getDate()));
            balance = debt.pay(Payment.of(balance, payment.getDate(), payment.getTicket()));
            dataService.save(debt);
        }

        if (balance.gt(Amount.ZERO)) {
            receipts.add(Receipt.of(balance, payment.getDate()));
            provisionRepository.save(
                    Provision.of(folderId, balance, Payment.of(balance, payment.getDate(), payment.getTicket())));
        }
        return receipts;
    }*/
}
