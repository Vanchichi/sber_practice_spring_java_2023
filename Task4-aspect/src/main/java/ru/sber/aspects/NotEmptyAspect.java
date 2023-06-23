package ru.sber.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Аспект, реализующий аннотацию NotEmpty
 */
@Aspect
@Component
public class NotEmptyAspect {
    private Logger logger = Logger.getLogger(NotEmptyAspect.class.getName());

    @Around("@annotation(ru.sber.aspects.NotEmpty)")
    public Object notEmptyValue(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] arguments = joinPoint.getArgs();

        boolean invalidArgument = IntStream.range(0, arguments.length)
                .anyMatch(i -> arguments[i] == null ||
                        (arguments[i] instanceof String && ((String) arguments[i]).isEmpty()) ||
                        (arguments[i] instanceof List && ((List<?>) arguments[i]).isEmpty()) ||
                        (arguments[i] instanceof Collection && ((Collection<?>) arguments[i]).isEmpty()));

        if (invalidArgument) {
            throw new IllegalArgumentException("Error invalid argument");
        }

        return joinPoint.proceed();
    }
}
