package com.ieng.task.dests.advice;


import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorBody {

    String code;
    String message;

    public ErrorBody(HttpStatus status, String message) {
        this.code = String.valueOf(status.value());
        this.message = message;
    }
}