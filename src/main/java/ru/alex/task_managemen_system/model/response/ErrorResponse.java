package ru.alex.task_managemen_system.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private String msg;

    private ZonedDateTime timestamp;

    private int numberException;

}
