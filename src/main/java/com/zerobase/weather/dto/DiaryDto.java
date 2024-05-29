package com.zerobase.weather.dto;

import com.zerobase.weather.entity.DiaryEntity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryDto {
    private Long id;
    private LocalDate date;
    private String text;

    public static DiaryDto fromEntity(DiaryEntity diaryEntity){
        return DiaryDto.builder()
                .id(diaryEntity.getId())
                .date(diaryEntity.getDate())
                .text(diaryEntity.getDiaryText())
                .build();
    }
}
