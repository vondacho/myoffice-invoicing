package edu.noia.myoffice.invoicing.domain.exception;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.exception.EntityNotFoundException;
import edu.noia.myoffice.common.util.exception.Problem;
import edu.noia.myoffice.invoicing.domain.command.debt.DebtCommand;
import edu.noia.myoffice.invoicing.domain.command.folder.FolderCommand;
import edu.noia.myoffice.invoicing.domain.event.debt.DebtProblemEventPayload;
import edu.noia.myoffice.invoicing.domain.event.folder.FolderProblemEventPayload;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolationException;

@Slf4j
@RequiredArgsConstructor
public class DomainExceptionHandler {

    @NonNull
    EventPublisher eventPublisher;

    public void handle(Command command, Throwable cause) {
        LOG.warn(cause.getMessage());
        if (command instanceof FolderCommand) {
            handleException((FolderCommand) command, cause);
        } else if (command instanceof DebtCommand) {
            handleException((DebtCommand) command, cause);
        } else {
            handleException(cause);
        }
    }

    private void handleException(Throwable cause) {
        if (cause.getCause() instanceof ConstraintViolationException) {
            eventPublisher.publish(new ProblemEventPayload(
                    Problem.from((ConstraintViolationException) (cause.getCause()))));
        }
    }

    private void handleException(FolderCommand command, Throwable cause) {
        if (cause.getCause() instanceof EntityNotFoundException) {
            eventPublisher.publish(new FolderProblemEventPayload(
                    Problem.from((EntityNotFoundException) (cause.getCause())), command.getFolderId()));
        }
    }

    private void handleException(DebtCommand command, Throwable cause) {
        if (cause.getCause() instanceof EntityNotFoundException) {
            eventPublisher.publish(new DebtProblemEventPayload(
                    Problem.from((EntityNotFoundException) (cause.getCause())), command.getDebtId()));
        }
    }
}
