package com.zerobase.weather.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ErrorResponse {
    private final ErrorCode errorCode;
    private final String errorMessage;
    private String fieldError;
}
