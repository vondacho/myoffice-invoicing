package edu.noia.myoffice.invoicing.domain.command.payment;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.invoicing.domain.vo.PaymentId;

public interface PaymentCommand extends Command {
    PaymentId getPaymentId();
}
