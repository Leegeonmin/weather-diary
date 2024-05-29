package com.zerobase.weather.exception;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryException extends  RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public DiaryException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getDescription();
    }
}
