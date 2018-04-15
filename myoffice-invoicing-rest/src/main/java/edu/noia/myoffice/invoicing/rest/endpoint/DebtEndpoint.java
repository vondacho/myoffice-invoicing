package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.common.domain.command.CommandPublisher;
import edu.noia.myoffice.invoicing.domain.command.debt.ValidateDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.InvoiceCartCommand;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import edu.noia.myoffice.invoicing.domain.vo.InvoiceSpecification;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/invoicing/v1/debts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtEndpoint {

    @NonNull
    CommandPublisher commandPublisher;

    @PostMapping
    public ResponseEntity create(@RequestBody InvoiceSpecification invoiceSpecification) {
        commandPublisher.accept(InvoiceCartCommand.of(
                invoiceSpecification.getCartId(),
                invoiceSpecification.getFolderId(),
                invoiceSpecification.getAmount()));
        return noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity validate(@PathVariable DebtId id,
                                   @RequestBody DebtSample debtSample) {
        commandPublisher.accept(ValidateDebtCommand.of(id, debtSample));
        return noContent().build();
    }
}
