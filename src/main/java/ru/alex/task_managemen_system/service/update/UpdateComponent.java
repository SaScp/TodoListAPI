package ru.alex.task_managemen_system.service.update;

import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.user.User;

public interface UpdateComponent {

    void execute(UpdateDTO updateDTO, User user);
}
