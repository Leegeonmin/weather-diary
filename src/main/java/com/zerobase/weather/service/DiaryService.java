package com.zerobase.weather.service;

import com.zerobase.weather.dto.DiaryDto;
import com.zerobase.weather.entity.DiaryEntity;
import com.zerobase.weather.entity.WeatherEntity;
import com.zerobase.weather.exception.DiaryException;
import com.zerobase.weather.repository.DiaryRepository;
import com.zerobase.weather.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.json.simple.parser.JSONParser;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zerobase.weather.exception.ErrorCode.DIARY_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class DiaryService {

    private final WeatherRepository weatherRepository;
    private final DiaryRepository diaryRepository;
    @Value("${openweathermap.key}")
    private String apiKey;


    @Transactional(readOnly = false)
    public DiaryDto insertDiary(LocalDate date, String text) {
        WeatherEntity weather = getWeatherEntity(date);

        DiaryEntity diary = diaryRepository.save(DiaryEntity.createDiary(weather, text, date));
        return DiaryDto.fromEntity(diary);
    }

    private WeatherEntity getWeatherEntity(LocalDate date) {
        List<WeatherEntity> dates = weatherRepository.findAllByDate(date);
        WeatherEntity weather;
        if (dates.isEmpty()) {
            weather = getWeatherFromApi();
        } else {
            weather = dates.get(0);
        }
        return weather;
    }


    private WeatherEntity getWeatherFromApi() {
        //openweathermap에서 날씨 저장하기
        String weatherString = getWeatherString();

        //받아온 날씨 json 파싱하기
        Map<String, Object> parsedWeather = parseWeather(weatherString);
        WeatherEntity dateWeather = new WeatherEntity();
        dateWeather.setWeather(parsedWeather.get("main").toString());
        dateWeather.setDate(LocalDate.now());
        dateWeather.setIcon(parsedWeather.get("icon").toString());
        dateWeather.setTemperature((double) parsedWeather.get("temp"));

        return dateWeather;
    }

    private String getWeatherString() {
        try {
            String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;

            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            return response.toString();
        } catch (Exception e) {
            return "failed to get response";
        }
    }

    private Map<String, Object> parseWeather(String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> resultMap = new HashMap<>();
        JSONObject mainData = (JSONObject) jsonObject.get("main");
        resultMap.put("temp", mainData.get("temp"));
        JSONArray weatherArray = (JSONArray) jsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        resultMap.put("main", weatherData.get("main"));
        resultMap.put("icon", weatherData.get("icon"));

        return resultMap;
    }

    public List<DiaryDto> getDiary(LocalDate date) {
        List<DiaryEntity> diaries = diaryRepository.findAllByDate(date);
        return diaries.stream()
                .map(e -> DiaryDto.builder()
                        .text(e.getDiaryText())
                        .date(e.getDate())
                        .build())
                .collect(Collectors.toList());
    }

    public List<DiaryDto> getDiaries(LocalDate startDate, LocalDate endDate) {
        List<DiaryEntity> diaries = diaryRepository.findAllByDateBetween(startDate, endDate);
        return diaries.stream()
                .map(e -> DiaryDto.builder()
                        .text(e.getDiaryText())
                        .date(e.getDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public DiaryDto updateDiary(LocalDate updateDate, String newText) {
        List<DiaryEntity> date = diaryRepository.findAllByDate(updateDate);
        if (date.isEmpty()) {
            throw new DiaryException(DIARY_NOT_FOUND);
        }
        DiaryEntity updatedDiary = date.get(0);

        updatedDiary.updateDiary(newText);

        return DiaryDto.fromEntity(updatedDiary);

    }
}

