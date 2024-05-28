package com.zerobase.weather.repository;

import com.zerobase.weather.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository  extends JpaRepository<DiaryEntity, Long> {
}
