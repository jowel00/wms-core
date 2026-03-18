package com.wms.core.infrastructure.web.mapper;

import com.wms.core.domain.exception.BaseException;
import com.wms.core.infrastructure.web.dto.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.UUID;

@Component
public class ErrorMapper {

    //Excepciones de negocio
    public ErrorResponse toResponse(BaseException ex, String path, HttpStatus status){

        List<ErrorResponse.FieldErrorMessage> details = null;
        if (ex.getErrors() != null && !ex.getErrors().isEmpty()){
            details = ex.getErrors().entrySet().stream()
                    .map(entry -> new ErrorResponse.FieldErrorMessage(entry.getKey(), entry.getValue()))
                    .toList();
        }

        return new ErrorResponse(status.value(), ex.getCode(), ex.getMessage(), path, details);
    }

    //Errores de validacion de Spring @Valid
    public ErrorResponse toValidationResponse(MethodArgumentNotValidException ex, String path){
        List<ErrorResponse.FieldErrorMessage> details = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(e -> new ErrorResponse.FieldErrorMessage(e.getField(), e.getDefaultMessage()))
                .toList();

        return new ErrorResponse(400, "INVALID_FIELDS", "Datos de entrada inválidos", path, details);
    }

    //Errores query params faltantes
    public ErrorResponse toMissingParamResponse(MissingServletRequestParameterException ex, String path){
        return new ErrorResponse(
                400,
                "MISSING_PARAMETER",
                String.format("El parametro '%s' es obligatorio", ex.getParameterName()),
                path
        );
    }

    //Errores query params formato incorrecto
    public ErrorResponse toTypeMismatchResponse(MethodArgumentTypeMismatchException ex, String path){
        String parameterName = ex.getName();

        String detail;
        if (ex.getRequiredType() != null && ex.getRequiredType().equals(UUID.class))
            detail = "debe ser un UUID válido (ej: 550e8400-e29b-41d4-a716-446655440000)";
        else {
            detail = "tiene un formato incorrecto";
        }

        return new ErrorResponse(
                400,
                "INVALID_PARAMETER_TYPE",
                String.format("El parámetro '%s': %s", parameterName,detail),
                path
        );
    }

    //Errores genéricos reglas de negocio
    public ErrorResponse toBadRequestResponse(String code, String message, String path){
        return new ErrorResponse(400, code, message, path);
    }
}
