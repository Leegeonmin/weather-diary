package com.zerobase.weather.repository;

import com.zerobase.weather.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryRepository  extends JpaRepository<DiaryEntity, Long> {
    List<DiaryEntity> findAllByDate(LocalDate date);
    List<DiaryEntity> findAllByDateBetween(LocalDate date1, LocalDate date2);
    int deleteByDate(LocalDate date);
}
