package edu.noia.myoffice.invoicing.domain.service;

import edu.noia.myoffice.invoicing.domain.event.folder.PaymentReceivedEventPayload;
import edu.noia.myoffice.invoicing.domain.repository.DebtRepository;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PaymentDispatcher {

    @NonNull
    DebtRepository debtRepository;

    public void dispatch(PaymentReceivedEventPayload event) {

    }
}
