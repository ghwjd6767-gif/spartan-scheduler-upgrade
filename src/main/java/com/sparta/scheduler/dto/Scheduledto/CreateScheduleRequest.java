package com.sparta.scheduler.dto.Scheduledto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateScheduleRequest {
    // 일정이 저장할 정보들
    @NotBlank
    private String title;
    private String contents;

}
