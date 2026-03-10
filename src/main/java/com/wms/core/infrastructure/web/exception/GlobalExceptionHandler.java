package com.wms.core.infrastructure.web.exception;

import com.wms.core.infrastructure.web.dto.response.errorResponse.CsvErrorResponse;
import com.wms.core.infrastructure.web.dto.response.errorResponse.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.UUID;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Error 404 - Owner no encontrado
    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOwnerNotFound(
            OwnerNotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        "Not Found",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    // Error 404 - Warehouse no encontrado
    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWarehouseNotFound(
            WarehouseNotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        "Not Found",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    // Error 404 - Location no encontrado
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLocationNotFound(
            LocationNotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        "Not Found",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    // Error 404 - Product no encontrado
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        404,
                        "Not Found",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    // Error 400 - @Valid en body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + " : " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        "Bad Request",
                        message,
                        request.getRequestURI()
                ));
    }

    // Error 400 - Query params faltantes
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        "Bad Request",
                        ex.getParameterName() + " is required",
                        request.getRequestURI()
                ));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String message;

        if (ex.getRequiredType() != null && ex.getRequiredType().equals(UUID.class)) {
            message = "Invalid UUID format for parameter: " + ex.getName();
        } else {
            message = "Invalid parameter: " + ex.getName();
        }

        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        "Bad Request",
                        message,
                        request.getRequestURI()
                ));
    }

    // Error 400 - Reglas de negocio
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        400,
                        "Bad Request",
                        ex.getMessage(),
                        request.getRequestURI()
                ));
    }

    // Error 400 - Errores de validación en carga masiva de CSV (con detalle por fila)
    @ExceptionHandler(CsvParseException.class)
    public ResponseEntity<CsvErrorResponse> handleCsvParseException(
            CsvParseException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(new CsvErrorResponse(
                        "El archivo CSV contiene errores de validación",
                        ex.getErrors(),
                        request.getRequestURI()
                ));
    }

    // Error 409 - Violación de restricción única en BD (ej. SKU ya existe para este Owner)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        409,
                        "Conflict",
                        "Uno o más SKUs ya existen para este Owner en la base de datos",
                        request.getRequestURI()
                ));
    }

}
