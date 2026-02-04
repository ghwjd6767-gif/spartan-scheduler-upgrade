package com.sparta.scheduler.dto;

import lombok.Getter;

@Getter
public class CreateScheduleRequest {

    private String name;
    private String password;
    private String title;
    private String contents;


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }
}
