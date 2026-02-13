package com.sparta.scheduler.service;

import com.sparta.scheduler.dto.Scheduledto.*;
import com.sparta.scheduler.entity.Schedule;
import com.sparta.scheduler.entity.User;
import com.sparta.scheduler.repository.ScheduleRepository;
import com.sparta.scheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public CreateScheduleResponse save(CreateScheduleRequest request, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );
        Schedule schedule = new Schedule(request.getTitle(),request.getContents(), user);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new CreateScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getUser().getName(),
                savedSchedule.getTitle(),
                savedSchedule.getContents(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public GetSchduleResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정 입니다.")
        );
        return new GetSchduleResponse(
                schedule.getId(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getContents()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchduleResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();

        List<GetSchduleResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedules) {
            GetSchduleResponse dto = new GetSchduleResponse(
                    schedule.getId(),
                    schedule.getUser().getName(),
                    schedule.getTitle(),
                    schedule.getContents()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    public UpdateScheduleResponse update(Long scheduleId, UpdateScheduleRequest request,Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정 입니다.")
        );

        if (!sessionUserId.equals(schedule.getUser().getId())) {
            throw new IllegalStateException("권한이 없습니다.");
        }

        schedule.updateContents(request.getTitle(),request.getContents());
        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }
    public void delete(Long scheduleId,Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다.")
        );

        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("없는 일정 입니다.")
        );

        if (!sessionUserId.equals(schedule.getUser().getId())) {
            throw new IllegalStateException("권한이 없습니다.");
        }

        // 존재하면
        scheduleRepository.deleteById(scheduleId);
    }
}
