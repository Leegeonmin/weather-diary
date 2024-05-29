package com.zerobase.weather.service;

import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.entity.DiaryEntity;
import com.zerobase.weather.entity.WeatherEntity;
import com.zerobase.weather.exception.DiaryException;
import com.zerobase.weather.exception.ErrorCode;
import com.zerobase.weather.repository.DiaryRepository;
import com.zerobase.weather.repository.WeatherRepository;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("일기 추가 성공")
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
                        .date(LocalDate.of(2000, 12, 12))
                        .icon("icon")
                        .temperature(20L)
                        .id(1L)
                        .build());
        //when
        DiaryDto diaryDto = diaryService.insertDiary(LocalDate.of(2000, 12, 12), "text");
        //then
        assertEquals(diaryDto.getDate(), LocalDate.of(2000, 12, 12));

    }


    @Test
    @DisplayName("일기 조회 성공")
    void successGetDiaries() {
        //given
        LocalDate date = LocalDate.of(2000, 12, 12);
        List<DiaryEntity> diaryDtoList = new ArrayList<>();
        diaryDtoList.add(DiaryEntity.builder().diaryText("첫번째일기").date(date).build());
        diaryDtoList.add(DiaryEntity.builder().diaryText("두번째일기").date(date).build());

        given(diaryRepository.findAllByDate(any(LocalDate.class)))
                .willReturn(diaryDtoList);
        //when
        List<DiaryDto> diaries = diaryService.getDiary(LocalDate.of(2000, 12, 12));
        //then
        assertEquals(diaries.size(), 2);
        assertEquals(diaries.get(0).getText(), "첫번째일기");

    }

    @Test
    @DisplayName("구간 일기 조회 성공")
    void successGetDiariesBetween() {
        //given
        LocalDate startDate = LocalDate.of(2000, 12, 12);
        LocalDate endDate = LocalDate.of(2000, 12, 13);
        List<DiaryEntity> diaryDtoList = new ArrayList<>();
        diaryDtoList.add(DiaryEntity.builder().diaryText("첫번째일기").date(startDate).build());
        diaryDtoList.add(DiaryEntity.builder().diaryText("두번째일기").date(endDate).build());

        given(diaryRepository.findAllByDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(diaryDtoList);
        //when
        List<DiaryDto> diaries = diaryService.getDiaries(startDate, endDate);
        //then
        assertEquals(diaries.size(), 2);
        assertEquals(diaries.get(0).getText(), "첫번째일기");
    }

    @Test
    @DisplayName("일기 수정 성공")
    void successUpdateDiary() {
        //given
        String updatedText = "업데이트될 내용";
        LocalDate date = LocalDate.of(2000, 12, 12);
        List<DiaryEntity> diaryDtoList = new ArrayList<>();
        diaryDtoList.add(DiaryEntity.builder().diaryText("첫번째일기").date(date).build());
        diaryDtoList.add(DiaryEntity.builder().diaryText("두번째일기").date(date).build());

        given(diaryRepository.findAllByDate(any(LocalDate.class)))
                .willReturn(diaryDtoList);
        //when
        DiaryDto diaries = diaryService.updateDiary(date, updatedText);
        //then
        assertEquals(diaries.getText(), updatedText);
    }

    @Test
    @DisplayName("일기 수정 실패(Date에 맞는 일기가 없음")
    void failUpdateDiary_DATEUNMATCH() {
        //given
        String updatedText = "업데이트될 내용";
        LocalDate updateDate = LocalDate.of(2000, 12, 10);

        given(diaryRepository.findAllByDate(any(LocalDate.class)))
                .willReturn(new ArrayList<>());
        //when
        DiaryException diaryException = assertThrows(DiaryException.class,
                () -> diaryService.updateDiary(updateDate, updatedText));

        //then
        assertEquals(ErrorCode.DIARY_NOT_FOUND, diaryException.getErrorCode());
    }
}