package com.sparta.scheduler.dto.Scheduledto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    // 선택한 일정 수정 일정 제목과 작성자명만 수정 가능하도록 요청
    private String title;
    private String contents;

}
