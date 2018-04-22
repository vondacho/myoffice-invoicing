package edu.noia.myoffice.invoicing.rest.endpoint;

import edu.noia.myoffice.common.domain.command.CommandPublisher;
import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.invoicing.domain.command.debt.CancelDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.RecallDebtCommand;
import edu.noia.myoffice.invoicing.domain.command.debt.ValidateDebtCommand;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.DebtSample;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/invoicing/v1/debts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtEndpoint {

    @NonNull
    CommandPublisher commandPublisher;

    @PutMapping("/{id}")
    public ResponseEntity validate(@PathVariable DebtId id,
                                   @RequestBody(required = false) DebtSample debtSample) {
        if (debtSample.getFolderId() != null) {
            commandPublisher.accept(ValidateDebtCommand.of(id, debtSample));
        }
        else {
            commandPublisher.accept(ValidateDebtCommand.of(id));
        }
        return noContent().build();
    }

    @PutMapping("/{id}/recall")
    public ResponseEntity recall(@PathVariable DebtId id) {
        commandPublisher.accept(RecallDebtCommand.of(id));
        return noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity cancel(@PathVariable DebtId id) {
        commandPublisher.accept(CancelDebtCommand.of(id));
        return noContent().build();
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(DebtId.class,
                new IdentifiantPropertyEditorSupport<>(s -> DebtId.of(UUID.fromString(s))));
    }
}
