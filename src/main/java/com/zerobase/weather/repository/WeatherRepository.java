package com.zerobase.weather.repository;

import com.zerobase.weather.entity.WeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherEntity, LocalDate> {
    List<WeatherEntity> findAllByDate(LocalDate date);
}
