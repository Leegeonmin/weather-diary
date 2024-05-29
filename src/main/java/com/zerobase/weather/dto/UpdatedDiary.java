package com.zerobase.weather.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatedDiary {
    private String text;
    private LocalDate date;
}
