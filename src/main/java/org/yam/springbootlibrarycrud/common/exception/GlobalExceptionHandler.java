package org.yam.springbootlibrarycrud.common.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //MethodArgumentNotValidException is typically triggered by @Valid @RequestBody whene API does not meet validation constraints like @NotBlank, @Size, etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        // Collect validation errors in a structured list
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Error");
        problemDetail.setDetail("One or more fields failed validation.");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false)); // API path
        problemDetail.setProperty("errorCode", "VALIDATION_FAILED"); // Custom error code
        problemDetail.setProperty("errors", errors); // Add detailed validation messages

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    //ConstraintViolationException: fails in contexts other than request body validation (more general context)
    // ++++ can also be triggrd by @valid but in diffrent context like manualy validate objects Validator.validate(object).
    // ++++ When validating method parameters in service methods
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        // Gather validation errors into a structured list
        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setDetail("One or more fields failed validation.");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false)); // API path
        problemDetail.setProperty("errorCode", "VALIDATION_FAILED"); // Custom error code
        problemDetail.setProperty("errors", errors); // Add detailed validation messages

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    //handle CONFLICT 409 already exist
    @ExceptionHandler(org.yam.springbootlibrarycrud.common.exception.ResourceConflictException.class)
    public ResponseEntity<ProblemDetail> handleResourceConflict(org.yam.springbootlibrarycrud.common.exception.ResourceConflictException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Resource Conflict");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false));
        problemDetail.setProperty("errorCode", "RESOURCE_CONFLICT");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    //handle NOT_FOUND 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Entity Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false)); // API path
        problemDetail.setProperty("errorCode", "ENTITY_NOT_FOUND"); // Custom error code

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false));
        problemDetail.setProperty("errorCode", "INVALID_ARGUMENT");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    // Handle INTERNAL_SERVER_ERROR 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleInternalServerError(Exception ex, WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("path", request.getDescription(false)); // API path

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }


}
