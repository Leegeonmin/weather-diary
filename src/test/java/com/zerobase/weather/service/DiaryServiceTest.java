package com.zerobase.weather.service;

import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.entity.DiaryEntity;
import com.zerobase.weather.entity.WeatherEntity;
import com.zerobase.weather.repository.DiaryRepository;
import com.zerobase.weather.repository.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {
    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private DiaryService diaryService;

    @Test
    void successInsertDiary() {
        //given
        List<WeatherEntity> list = new ArrayList<>();
        list.add(WeatherEntity.builder()
                .weather("weather")
                .date(LocalDate.of(2000, 12, 12))
                .icon("icon")
                .temperature(20L)
                .build());
        given(weatherRepository.findAllByDate(any(LocalDate.class)))
                .willReturn(list);
        given(diaryRepository.save(any(DiaryEntity.class)))
                .willReturn(DiaryEntity.builder()
                        .diaryText("diaryText")
                        .date(LocalDate.of(2000,12,12))
                        .icon("icon")
                        .temperature(20L)
                        .id(1L)
                        .build());
        //when
        DiaryDto diaryDto = diaryService.insertDiary(LocalDate.of(2000, 12, 12), "text");
        //then
        assertEquals(diaryDto.getDate(), LocalDate.of(2000, 12, 12));

    }
}