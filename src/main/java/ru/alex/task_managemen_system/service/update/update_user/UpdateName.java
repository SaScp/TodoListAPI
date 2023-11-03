package ru.alex.task_managemen_system.service.update.update_user;

import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.user.User;

import java.util.Optional;

public class UpdateName implements UpdateComponent {
    @Override
    public void execute(UpdateDTO updateDTO, User user) {
        if (Optional.ofNullable(updateDTO.getName()).isPresent()) {
            user.setName(updateDTO.getName());
        }
    }
}
