package ru.alex.task_managemen_system.model.response;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorResponse {

    private String msg;

    private ZonedDateTime timestamp;

    private int numberException;

}
