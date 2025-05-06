package com.toms.toms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiError {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
