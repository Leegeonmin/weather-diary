package com.zerobase.weather.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Entity(name = "weather")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class WeatherEntity {
    @Id
    private LocalDate date;
    private String weather;
    private String icon;
    private double temperature;
}
