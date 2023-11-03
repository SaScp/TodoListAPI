package ru.alex.task_managemen_system.service.update.update_task;

import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.task.Task;
import ru.alex.task_managemen_system.model.user.User;

import java.util.Optional;

public class UpdateStatus implements UpdateComponent {

    @Override
    public void execute(TaskDTO taskDTO, Task task) {
        if (taskDTO.getDescription() != null) {
            task.setStatus(taskDTO.getStatus());
        }
    }
}
