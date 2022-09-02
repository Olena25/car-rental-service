package com.intellias.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "deposit code does not exist")
public class DepositCodeNotFoundException extends RuntimeException{
}
