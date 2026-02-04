package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<schedule, Long> {
}
