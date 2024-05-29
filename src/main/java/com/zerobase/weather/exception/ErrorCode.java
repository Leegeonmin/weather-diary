package com.zerobase.weather.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DIARY_NOT_FOUND("일치하는 일기가 없습니다"),
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다"),
    INVALID_REQUEST("잘못된 요청입니다");

    private final String description;

}
