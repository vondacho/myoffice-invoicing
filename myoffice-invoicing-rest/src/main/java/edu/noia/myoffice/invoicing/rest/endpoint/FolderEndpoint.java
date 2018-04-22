package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.common.domain.command.CommandPublisher;
import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.invoicing.domain.command.payment.CancelPaymentCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.PayDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.*;
import edu.noia.myoffice.invoicing.domain.command.ticket.CancelTicketCommand;
import edu.noia.myoffice.invoicing.domain.vo.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/invoicing/v1/folders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FolderEndpoint {

    @NonNull
    CommandPublisher commandPublisher;

    @PostMapping
    public ResponseEntity create(@RequestBody FolderId folderId) {
        commandPublisher.accept(CreateFolderCommand.of(folderId));
        return noContent().build();
    }

    @PutMapping("/{id}/affiliate")
    public ResponseEntity affiliate(@PathVariable FolderId id, @RequestBody Affiliate affiliate) {
        commandPublisher.accept(AffiliateCommand.of(id, affiliate));
        return noContent().build();
    }

    @PutMapping("/{id}/invoice")
    public ResponseEntity charge(@PathVariable FolderId id, @RequestBody Invoice invoice) {
        commandPublisher.accept(InvoiceCartCommand.of(id, invoice.getCartId(), invoice.getAmount()));
        return noContent().build();
    }

    @PutMapping("/{id}/request")
    public ResponseEntity ask(@PathVariable FolderId id, @RequestBody Request request) {
        commandPublisher.accept(AskCommand.of(id, request.getAmount()));
        return noContent().build();
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity pay(@PathVariable FolderId id, @RequestBody Payment payment, @RequestParam DebtId[] debts) {
        commandPublisher.accept(PayDebtCommand.of(id, payment, Arrays.asList(debts)));
        return noContent().build();
    }

    @PutMapping("/{id}/ticket")
    public ResponseEntity register(@PathVariable FolderId id, @RequestBody Ticket ticket) {
        commandPublisher.accept(RegisterTicketCommand.of(id, ticket));
        return noContent().build();
    }

    @DeleteMapping("/payments/{id}")
    public ResponseEntity cancel(@PathVariable PaymentId id) {
        commandPublisher.accept(CancelPaymentCommand.of(id));
        return noContent().build();
    }

    @DeleteMapping("/tickets/{ticket}")
    public ResponseEntity cancel(@PathVariable Ticket ticket) {
        commandPublisher.accept(CancelTicketCommand.of(ticket));
        return noContent().build();
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(FolderId.class,
                new IdentifiantPropertyEditorSupport<>(s -> FolderId.of(UUID.fromString(s))));
        binder.registerCustomEditor(PaymentId.class,
                new IdentifiantPropertyEditorSupport<>(s -> PaymentId.of(UUID.fromString(s))));
        binder.registerCustomEditor(Ticket.class,
                new IdentifiantPropertyEditorSupport<>(Ticket::of));
    }
}