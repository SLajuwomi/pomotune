package com.slajuwomi.pomotune.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler - Centralized error handling for the entire
 * application
 * <p>
 * This class catches exceptions thrown by controllers and converts them into
 * clean, user-friendly JSON responses instead of scary stack traces.
 *
 * @RestControllerAdvice - Applies to all controllers in the application
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle Validation Errors (Bean Validation failures)
     * <p>
     * This method catches @Valid annotation failures and converts them into clean
     * error messages that show EXACTLY what the user did wrong.
     *
     * @param ex The validation exception containing field errors
     * @return Clean JSON response with specific field error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {

        // Create a clean response structure
        Map<String, Object> response = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        // Extract each field error and its custom message
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        // Build user-friendly response
        response.put("status", "error");
        response.put("message", "Validation failed");
        response.put("errors", fieldErrors);

        // Return HTTP 400 Bad Request with clean error details
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle IllegalArgumentException (from your UserService validation)
     * <p>
     * This catches exceptions thrown by your business logic validation in
     * UserService.createUser() method.
     *
     * @param ex The exception containing the error message
     * @return Clean JSON response with the error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", "error");
        response.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Handle duplicate data
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataInegrityViolation(DataIntegrityViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", ex.getMessage());

        String errorMessage = ex.getMessage();

        if (errorMessage.contains("unique constrain") && errorMessage.contains("email")) {
            response.put("message", "A user with this email address already exists");
        } else if (errorMessage.contains("duplicate key")) {
            response.put("message", "This information is already in use");
        } else {
            response.put("message", "Database constraint violation occurred");
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    /**
     * Handle any unexpected errors
     * <p>
     * This is a safety net for any exceptions we didn't specifically handle. Better
     * to give users a clean "something went wrong" than a stack trace.
     *
     * @param ex Any unexpected exception
     * @return Generic error response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericError(Exception ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", "error");
        response.put("message", "An unexpected error occurred");

        // Log the actual error for debugging (but don't show user)
        System.err.println("Unexpected error: " + ex.getMessage());
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}