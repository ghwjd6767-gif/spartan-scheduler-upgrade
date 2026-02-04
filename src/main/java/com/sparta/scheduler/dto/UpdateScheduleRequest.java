package com.sparta.scheduler.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequest {

    private String title;
    private String name;

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
