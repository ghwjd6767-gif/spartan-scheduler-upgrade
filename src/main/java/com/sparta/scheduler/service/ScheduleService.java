package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.*;
import com.sparta.scheduler.entity.schedule;
import com.sparta.scheduler.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public CreateScheduleResponse save(CreateScheduleRequest request) {
        schedule schedule = new schedule(request.getName(),request.getPassword(),request.getTitle(),request.getContents());
        schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getName(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetSchduleResponse findOne(Long scheduleId) {
        schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정 입니다.")
        );
        return new GetSchduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContents()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchduleResponse> findAll() {
        List<schedule> schedules = scheduleRepository.findAll();

        List<GetSchduleResponse> dtos = new ArrayList<>();
        for (schedule schedule : schedules) {
            GetSchduleResponse dto = new GetSchduleResponse(
                    schedule.getId(),
                    schedule.getName(),
                    schedule.getTitle(),
                    schedule.getContents()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request) {
        schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정 입니다.")
        );

        schedule.updateContents(request.getTitle(),request.getName());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getName(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
    public void delete(Long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        // 존재하지 않으면
        if (!existence) {
            throw new IllegalStateException("없는 일정 입니다.");
        }

        // 존재하면
        scheduleRepository.deleteById(scheduleId);
    }
}
