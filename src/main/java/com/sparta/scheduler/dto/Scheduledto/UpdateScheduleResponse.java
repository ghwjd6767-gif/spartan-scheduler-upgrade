package com.sparta.scheduler.dto.Scheduledto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {

    private final Long id;
    private final String name;
    private final String title;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    // 선택한 일정 수정시에 보여지는 응답, 작성일은 변경할 수 없고
    // 수정일은 수정 완료시 수정한 시점으로 변경되도록
    public UpdateScheduleResponse(Long id, String name, String title, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
