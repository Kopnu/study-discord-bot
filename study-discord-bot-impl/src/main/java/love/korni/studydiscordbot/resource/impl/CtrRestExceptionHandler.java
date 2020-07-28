package love.korni.studydiscordbot.resource.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import love.korni.studydiscordbot.dto.ErrorResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * CtrRestExceptionHandler
 *
 * @author Sergei_Kornilov
 */

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class CtrRestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(RuntimeException ex) {
        return errorResponse(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> errorResponse(Exception e, HttpStatus httpStatus) {
        log.error("error - ", e);
        ErrorResponse error = new ErrorResponse(e.getMessage());

        return new ResponseEntity<>(error, new HttpHeaders(), httpStatus);
    }

    private String tryResolveMessageKey(String message) {
        try {
            return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            log.error("Message key not found '{}'", e.getMessage());
            return message;
        }
    }
}
