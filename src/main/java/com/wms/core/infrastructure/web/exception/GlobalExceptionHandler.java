package com.wms.core.infrastructure.web.exception;

import com.wms.core.domain.exception.*;
import com.wms.core.infrastructure.web.dto.response.error.CsvErrorResponse;
import com.wms.core.infrastructure.web.dto.response.error.ErrorResponse;
import com.wms.core.infrastructure.web.mapper.ErrorMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorMapper errorMapper;

    //Error 400 -> Menejo de reglas de negocio
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusinees(
            BusinessRuleException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.badRequest()
                .body(errorMapper.toResponse(ex, request.getRequestURI(), HttpStatus.BAD_REQUEST)
                );
    }

    //Error 400 -> Manejo de errores de validación @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.badRequest()
                .body(errorMapper.toValidationResponse(ex, request.getRequestURI())
                );
    }

    // Error 400 -> Query params faltantes
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.badRequest()
                .body(errorMapper.toMissingParamResponse(ex, request.getRequestURI())
                );
    }

    //Error 400 -> Tipos de datos erroneos
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.badRequest()
                .body(errorMapper.toTypeMismatchResponse(ex, request.getRequestURI())
                );
    }

    // Error 400 - Errores de validación en carga masiva de CSV (con detalle por fila)
    @ExceptionHandler(CsvParseException.class)
    public ResponseEntity<CsvErrorResponse> handleCsvParseException(
            CsvParseException ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.badRequest()
                .body(new CsvErrorResponse(
                        ex.getMessage(),
                        ex.getErrors(),
                        request.getRequestURI()
                ));
    }

    //Error 404 -> Manejo de error 404 de cualquier entidad
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorMapper.toResponse(ex,request.getRequestURI(), HttpStatus.NOT_FOUND)
                );
    }

    //Error 409 -> Manejo de error 409 de conflictos cualquier entidad
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(
            ResourceConflictException ex,
            HttpServletRequest request
    ){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorMapper.toResponse(ex, request.getRequestURI(), HttpStatus.CONFLICT)
                );
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        500,
                        "INTERNAL_ERROR",
                        "Ocurrió un error inesperado",
                        request.getRequestURI()
                ));
    }

}
