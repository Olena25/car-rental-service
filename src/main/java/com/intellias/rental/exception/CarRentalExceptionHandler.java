package com.intellias.rental.exception;

import com.intellias.rental.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class CarRentalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error handleValidationException(MethodArgumentNotValidException e) {
        final List<FieldError> fieldErrors = e.getFieldErrors();

        FieldError fieldError = fieldErrors.get(0);

        return new Error("Validation error for field [" + fieldError.getField() + "]");
    }

    @ExceptionHandler(DepositLimitExceedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Error handleDepositLimitExceedException(DepositLimitExceedException e){
        return new Error(e.getMessage());
    }

}
