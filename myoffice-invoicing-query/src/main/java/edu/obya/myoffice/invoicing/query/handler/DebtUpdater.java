package edu.obya.myoffice.invoicing.query.handler;

import edu.noia.myoffice.invoicing.domain.event.debt.*;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import edu.obya.myoffice.invoicing.query.repository.DebtStateRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import static edu.noia.myoffice.invoicing.domain.vo.DebtStatus.CLOSED;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtUpdater {

    @NonNull
    DebtStateRepository repository;

    public void created(InvoiceCreatedEventPayload event) {
        repository.save(event.getDebtId(), DebtSample.from(event.getDebtSample()));
    }

    public void validated(DebtValidatedEventPayload event) {
        repository
                .findById(event.getDebtId())
                .ifPresent(state -> state.modify(event.getDebtState()));
    }

    public void payed(PaymentDoneEventPayload event) {
        repository
                .findById(event.getDebtId())
                .ifPresent(state -> state.pay(event.getPayment()));
    }

    public void recalled(RecallEmittedEventPayload event) {
        repository
                .findById(event.getDebtId())
                .ifPresent(state -> state.addRecall(event.getRecall()));
    }

    public void closed(DebtClosedEventPayload event) {
        repository
                .findById(event.getDebtId())
                .ifPresent(state -> state.setStatus(CLOSED));
    }
}