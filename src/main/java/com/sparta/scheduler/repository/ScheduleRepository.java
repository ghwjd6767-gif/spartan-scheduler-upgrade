package com.sparta.scheduler.repository;

import com.sparta.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
// 일정 저장소 달력(스케줄러) 본체(?)
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
