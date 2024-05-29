package com.zerobase.weather.controller;

import com.zerobase.weather.dto.AddDiary;
import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.dto.GetDiary;
import com.zerobase.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("diary")
public class DiaryController {
    private final DiaryService diaryService;

    /**
     * POST / create / diary
     *
     * @param date
     * @param text
     * @return
     */
    @PostMapping
    public ResponseEntity<AddDiary.Response> addDiary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestBody String text) {

        DiaryDto diaryDto = diaryService.insertDiary(date, text);

        return ResponseEntity.ok(AddDiary.Response.builder()
                .id(diaryDto.getId())
                .date(diaryDto.getDate()).build());
    }

    /**
     * GET / read / diary
     * @param date
     * @return
     */
    @GetMapping
    public ResponseEntity<List<GetDiary.Response>> getDiary(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<DiaryDto> diaries = diaryService.getDiary(date);

        return ResponseEntity.ok(
                diaries.stream().map(e ->
                                GetDiary.Response
                                        .builder()
                                        .text(e.getText())
                                        .build())
                        .collect(Collectors.toList())
        );
    }

}

