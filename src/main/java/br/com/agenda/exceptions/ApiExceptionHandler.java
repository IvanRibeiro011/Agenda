package br.com.agenda.exceptions;

import br.com.agenda.exceptions.messages.ApiErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorMessage> handleRunTimeException(RuntimeException e, HttpServletRequest request) {
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ApiErrorMessage message = new ApiErrorMessage();
        message.setStatus(status);
        message.setTimestamp(java.time.Instant.now());
        message.setError("Internal Server Error");
        message.setMessage(e.getMessage());
        message.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(message);
    }
}
