package edu.noia.myoffice.invoicing.domain.exception;

import edu.noia.myoffice.common.domain.command.Command;
import edu.noia.myoffice.common.domain.event.EventPublisher;
import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.exception.Problem;
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
        handleException(cause);
    }

    private void handleException(Throwable cause) {
        if (cause.getCause() instanceof ConstraintViolationException) {
            eventPublisher.publish(new ProblemEventPayload(
                    Problem.from((ConstraintViolationException) (cause.getCause()))));
        }
    }
}
