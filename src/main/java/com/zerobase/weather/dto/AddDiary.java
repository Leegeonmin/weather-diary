package com.zerobase.weather.dto;

import lombok.*;

import java.time.LocalDate;


public class AddDiary {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
        private Long id;
        private LocalDate date;
    }
}
