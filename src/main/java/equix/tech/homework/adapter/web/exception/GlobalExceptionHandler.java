package equix.tech.homework.adapter.web.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import equix.tech.homework.adapter.dto.CommonResponse;
import equix.tech.homework.domain.exception.InvalidException;
import equix.tech.homework.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CommonResponse> handleOrderNotFound(NotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Not Found Exception");
    }

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<CommonResponse> handleInvalidOrderState(InvalidException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid Exception");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CommonResponse> handleInvalidFormat(HttpMessageNotReadableException ex) {
        String message = "Invalid request";

        // parse enum
        if (ex.getCause() instanceof InvalidFormatException formatEx) {
            Class<?> targetType = formatEx.getTargetType();
            if (targetType.isEnum()) {
                message = String.format(
                    "Invalid value '%s' for %s. Allowed values: %s",
                    formatEx.getValue(),
                    targetType.getSimpleName(),
                    String.join(", ", Arrays.stream(targetType.getEnumConstants()).map(Object::toString).toList())
                );
            }
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message, "Validation failed");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        // get first error each field
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError e : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.putIfAbsent(e.getField(), e.getDefaultMessage());
        }
        return buildErrorResponse(HttpStatus.BAD_REQUEST, fieldErrors, "Validation failed");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CommonResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String msg = String.format("Invalid parameter '%s': expected type %s", ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, msg, "Type mismatch");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> handleGenericError(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Internal server error");
    }

    private ResponseEntity<CommonResponse> buildErrorResponse(HttpStatus status, Object message, String reason) {
        CommonResponse response = new CommonResponse(
            message,
            reason,
            false
        );
        return ResponseEntity.status(status).body(response);
    }
}
