package com.zerobase.weather.dto;

import lombok.*;

import java.time.LocalDate;

public class GetDiary {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response{
        private String text;
        private LocalDate date;
    }
}
