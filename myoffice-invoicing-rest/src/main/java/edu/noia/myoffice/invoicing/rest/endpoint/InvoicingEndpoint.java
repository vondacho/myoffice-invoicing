package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.common.domain.command.CommandPublisher;
import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.invoicing.domain.command.folder.InvoiceCartCommand;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.InvoiceSpecification;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/invoicing/v1/debts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoicingEndpoint {

    @NonNull
    CommandPublisher commandPublisher;

    @PostMapping
    public ResponseEntity create(@RequestBody InvoiceSpecification invoiceSpecification) {
        commandPublisher.accept(InvoiceCartCommand.of(
                invoiceSpecification.getCartId(),
                invoiceSpecification.getFolderId(),
                invoiceSpecification.getCartAmount()));
        return noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") DebtId cartId) {
        return notFound().build();
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(DebtId.class,
                new IdentifiantPropertyEditorSupport<>(s -> DebtId.of(UUID.fromString(s))));
    }
}
