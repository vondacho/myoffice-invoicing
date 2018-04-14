package edu.noia.myoffice.invoicing.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LogServiceMethodCallAspect {

    @Autowired
    ObjectMapper objectMapper;

    @Pointcut("execution(* edu.noia.myoffice.invoicing.domain.command.InvoicingCommandHandler.*(..))")
    public void serviceMethods() {
    }

    @Before("serviceMethods()")
    public void logMethodCall(JoinPoint jp) {
        try {
            LOG.debug("{} is called with {}", jp.getSignature().getName(), objectMapper.writeValueAsString(jp.getArgs()));
        } catch (JsonProcessingException e) {
        }
    }
}
