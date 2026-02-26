package edgar.technical.test.isp.unitconverter.exception;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edgar.technical.test.isp.unitconverter.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleInvalidEnum(
      HttpMessageNotReadableException ex,
      HttpServletRequest req) {

    Throwable cause = ex.getCause();

    if (cause instanceof com.fasterxml.jackson.databind.exc.InvalidFormatException ife
        && ife.getTargetType().isEnum()) {

      String field = ife.getPath().get(0).getFieldName();
      String msg = field + ": invalid value";

      return build(HttpStatus.BAD_REQUEST, msg, req.getRequestURI());
    }

    return build(HttpStatus.BAD_REQUEST, "Malformed JSON request", req.getRequestURI());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage())
        .collect(Collectors.joining(", "));

    return build(HttpStatus.BAD_REQUEST, msg, req.getRequestURI());
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<ErrorResponse> handleBind(BindException ex, HttpServletRequest req) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .map(err -> err.getField() + ": " + err.getDefaultMessage())
        .collect(Collectors.joining(", "));

    return build(HttpStatus.BAD_REQUEST, msg, req.getRequestURI());
  }

  @ExceptionHandler({ UnsupportedUnitException.class, InvalidConversionException.class })
  public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException ex, HttpServletRequest req) {
    return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
  }

  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<ErrorResponse> handleExternal(ExternalServiceException ex, HttpServletRequest req) {
    HttpStatus status = HttpStatus.BAD_GATEWAY;
    String msg = ex.getMessage() + " (status=" + ex.getStatusCode() + ")";
    return build(status, msg, req.getRequestURI());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest req) {
    return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI());
  }

  private ResponseEntity<ErrorResponse> build(HttpStatus status, String message, String path) {
    ErrorResponse body = new ErrorResponse(
        OffsetDateTime.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        path);
    return ResponseEntity.status(status).body(body);
  }
}