package com.zerobase.weather.controller;

import com.zerobase.weather.dto.AddDiary;
import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.dto.GetDiary;
import com.zerobase.weather.dto.UpdatedDiary;
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
     * 일기 작성 API
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
     * 하루의 일기 조회 API
     *
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
                                        .date(e.getDate())
                                        .build())
                        .collect(Collectors.toList())
        );
    }

    /**
     * GET / read / diaries
     * 구간 일기 조회 API
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/between")
    public ResponseEntity<List<GetDiary.Response>> getDiaries(
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<DiaryDto> diaries = diaryService.getDiaries(startDate, endDate);

        return ResponseEntity.ok(
                diaries.stream().map(e ->
                                GetDiary.Response
                                        .builder()
                                        .text(e.getText())
                                        .date(e.getDate())
                                        .build())
                        .collect(Collectors.toList())
        );
    }


    /**
     * PUT / update / diary
     * 일기 수정 API
     * @param updateDate
     * @param newText
     * @return
     */
    @PutMapping
    public ResponseEntity<UpdatedDiary> updateDiary(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate updateDate,
            @RequestBody String newText) {
        DiaryDto updateDiary = diaryService.updateDiary(updateDate, newText);

        return ResponseEntity.ok(UpdatedDiary.builder()
                .text(updateDiary.getText())
                .date(updateDiary.getDate())
                .build());
    }
}

