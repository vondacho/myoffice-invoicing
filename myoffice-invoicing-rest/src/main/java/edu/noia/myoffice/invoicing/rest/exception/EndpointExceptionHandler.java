package edu.noia.myoffice.invoicing.rest.exception;

import edu.noia.myoffice.common.util.exception.EntityNotFoundException;
import edu.noia.myoffice.common.util.exception.Problem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@ControllerAdvice
@Slf4j
public class EndpointExceptionHandler {

    private static final String VIOLATION_MESSAGE = "Constraint Violation : {}";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public List<Problem> invalidArgument(final HttpMessageNotReadableException exception) {
        LOG.warn(VIOLATION_MESSAGE, exception.getMessage());
        return Problem.from(exception);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public List<Problem> invalidArgument(final MethodArgumentNotValidException exception) {
        LOG.warn(VIOLATION_MESSAGE, exception.getMessage());
        Optional<FieldError> optionalFieldError =
                Optional.ofNullable(exception.getBindingResult()).map(Errors::getFieldError);

        if (optionalFieldError.isPresent()) {
            FieldError fieldError = optionalFieldError.get();
            return Collections.singletonList(new Problem(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return Problem.from(exception);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public List<Problem> constraintViolation(final ConstraintViolationException exception) {
        LOG.warn(VIOLATION_MESSAGE, exception.getMessage());
        return Problem.from(exception);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public List<Problem> dataIntegrityViolation(final DataIntegrityViolationException exception) {
        LOG.warn(VIOLATION_MESSAGE, exception.getMessage());
        return Problem.from(exception);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public List<Problem> treatMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        Exception e = exception;
        Throwable rootCause = exception.getRootCause();
        if (rootCause != null && rootCause instanceof Exception) {
            e = (Exception) rootCause;
        }
        return Problem.from(e);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public List<Problem> internalServerError(final Exception exception) {
        LOG.error("Oops! Something went wrong...", exception);
        return Problem.from(exception);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public List<Problem> managedException(final Exception exception) {
        return Problem.from(exception);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public List<Problem> resourceNotFound(final Exception exception) {
        return Problem.from(exception);
    }

}
