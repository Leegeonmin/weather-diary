package com.zerobase.weather.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity(name="diary")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diaryText;
    private String weather;
    private String icon;
    private double temperature;
    private LocalDate date;

    public static DiaryEntity createDiary(WeatherEntity weather, String text, LocalDate date){
        return DiaryEntity.builder()
                .diaryText(text)
                .date(date)
                .weather(weather.getWeather())
                .icon(weather.getIcon())
                .temperature(weather.getTemperature())
                .build();
    }
}
