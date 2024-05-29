package com.zerobase.weather.repository;

import com.zerobase.weather.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository  extends JpaRepository<DiaryEntity, Long> {
    Optional<List<DiaryEntity>> findAllByDate(LocalDate date);
    List<DiaryEntity> findAllByDateBetween(LocalDate date1, LocalDate date2);
}
