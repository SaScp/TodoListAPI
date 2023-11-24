package ru.alex.task_managemen_system.service.update.update_task;

import ru.alex.task_managemen_system.model.dto.task.TaskDTO;

import java.util.Optional;

public class UpdateStatus implements UpdateComponent {

    @Override
    public void execute(TaskDTO taskDTO, ru.alex.task_managemen_system.model.task.Task task) {
        if (Optional.ofNullable(taskDTO.getStatus()).isPresent()) {
            task.setStatus(taskDTO.getStatus());
        }
    }
}
