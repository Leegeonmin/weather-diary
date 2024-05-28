package com.zerobase.weather.controller;

import com.zerobase.weather.dto.AddDiary;
import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("diary")
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<AddDiary.Response> addDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text) {

        DiaryDto diaryDto = diaryService.insertDiary(date, text);

        return ResponseEntity.ok(AddDiary.Response.builder()
                .id(diaryDto.getId())
                .date(diaryDto.getDate()).build());
    }
}

