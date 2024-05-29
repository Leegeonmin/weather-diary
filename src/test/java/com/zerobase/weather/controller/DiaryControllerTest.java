package com.zerobase.weather.controller;

import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.service.DiaryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DiaryController.class)
class DiaryControllerTest {
    @MockBean
    private DiaryService diaryService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("일기 작성 성공")
    void successCreateDiary() throws Exception {
        String text = "오늘의 일기 내용 ";
        LocalDate date = LocalDate.of(2024, 5, 29);
        //given
        given(diaryService.insertDiary(any(LocalDate.class), anyString()))
                .willReturn(DiaryDto.builder()
                        .id(1L)
                        .date(date)
                        .build());
        //when
        //then
        mockMvc.perform(post("/diary?date=2024-05-29")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(text))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.date").value(date.toString()));
    }

    @Test
    @DisplayName("한 날짜 일기 조회 API 성공")
    void successgetOneDateDiaries() throws Exception {
        LocalDate date = LocalDate.of(2024, 5, 29);
        List<DiaryDto> diaryDtoList = new ArrayList<>();
        diaryDtoList.add(DiaryDto.builder().text("첫번째일기").date(date).build());
        diaryDtoList.add(DiaryDto.builder().text("두번째일기").date(date).build());
        //given
        given(diaryService.getDiary(any(LocalDate.class)))
                .willReturn(diaryDtoList);
        //when
        //then
        mockMvc.perform(get("/diary?date=2024-05-29")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("첫번째일기"))
                .andExpect(jsonPath("$[0].date").value(date.toString()));
    }

    @Test
    @DisplayName("구간 날짜 일기 조회 API 성공")
    void successgetBetweenDateDiaries() throws Exception {
        LocalDate date = LocalDate.of(2024, 5, 29);
        List<DiaryDto> diaryDtoList = new ArrayList<>();
        diaryDtoList.add(DiaryDto.builder().text("첫번째일기").date(date).build());
        diaryDtoList.add(DiaryDto.builder().text("두번째일기").date(date.plusDays(1)).build());
        //given
        given(diaryService.getDiaries(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(diaryDtoList);
        //when
        //then
        mockMvc.perform(get("/diary/between?startDate=2024-05-29&endDate=2024-05-30")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("첫번째일기"))
                .andExpect(jsonPath("$[0].date").value(date.toString()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("일기 수정 API 성공")
    void successgetUpdateDiary() throws Exception {
        String newText = "변경될 일기 내용";
        LocalDate date = LocalDate.of(2024, 5, 29);

        //given
        given(diaryService.updateDiary(any(LocalDate.class), anyString()))
                .willReturn(DiaryDto.builder()
                        .id(1L)
                        .text(newText)
                        .date(date)
                        .build());
        //when
        //then
        mockMvc.perform(put("/diary?date=2024-05-29")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newText))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(newText))
                .andExpect(jsonPath("$.date").value(date.toString()));
    }

    @Test
    @DisplayName("일기 삭제 API 성공")
    void successDeleteDiary() throws Exception {

        //given
        given(diaryService.deleteDiary(any(LocalDate.class)))
                .willReturn(3);
        //when
        //then
        mockMvc.perform(delete("/diary?date=2024-05-29")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("3 Diaries deleted."));

    }
}