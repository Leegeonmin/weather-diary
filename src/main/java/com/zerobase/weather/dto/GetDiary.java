package com.zerobase.weather.dto;

import lombok.*;

import java.time.LocalDate;

public class GetDiary {
    @Builder
    @Getter
    public static class Request{
        private LocalDate date;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String text;
    }
}
