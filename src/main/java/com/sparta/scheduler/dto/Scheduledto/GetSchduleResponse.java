package com.sparta.scheduler.dto.Scheduledto;

import lombok.Getter;

@Getter
public class GetSchduleResponse {

    private final Long id;
    private final String name;
    private final String title;
    private final String contents;
    // 조회한 일정이 보여주는 정보 (응답)
    public GetSchduleResponse(Long id, String name, String title, String contents) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.contents = contents;
    }
}
