package com.sparta.scheduler.dto;

import lombok.Getter;

@Getter
public class GetSchduleResponse {

    private final Long id;
    private final String name;
    private final String title;
    private final String contents;

    public GetSchduleResponse(Long id, String name, String title, String contents) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.contents = contents;
    }
}
