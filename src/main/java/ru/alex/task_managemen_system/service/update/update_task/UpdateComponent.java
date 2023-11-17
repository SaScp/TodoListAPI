package ru.alex.task_managemen_system.service.update.update_task;

import ru.alex.task_managemen_system.model.dto.task.TaskDTO;
import ru.alex.task_managemen_system.model.task.Task;


public interface UpdateComponent {

    void execute(TaskDTO taskDTO, Task task);
}
