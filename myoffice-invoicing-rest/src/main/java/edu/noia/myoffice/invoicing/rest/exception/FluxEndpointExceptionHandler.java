package edu.noia.myoffice.invoicing.rest.exception;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.common.ExceptionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RequestMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
@ControllerAdvice
@Slf4j
public class FluxEndpointExceptionHandler {

    @ExceptionHandler(IOException.class)
    public Object brokenPipeHandler(IOException exception) {
        LOG.warn("Maybe a broken pipe", exception.getMessage());
        return ExceptionUtils.findException(exception, ee -> ee.getMessage().contains("Broken pipe")).isPresent() ?
                ResponseEntity.status(SERVICE_UNAVAILABLE) :
                ResponseEntity.status(SERVICE_UNAVAILABLE).body(exception.getMessage());
    }
}
