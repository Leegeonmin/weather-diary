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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        String text = "오늘의 일기 내용";
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
}