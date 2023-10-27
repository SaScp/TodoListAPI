package ru.alex.task_managemen_system.service.update;

import ru.alex.task_managemen_system.model.dto.UpdateDTO;
import ru.alex.task_managemen_system.model.user.User;

import java.util.Optional;

public class UpdateEmail implements UpdateComponent {
    @Override
    public void execute(UpdateDTO updateDTO, User user) {
        if (Optional.ofNullable(updateDTO.getEmail()).isPresent()) {
            user.setEmail(updateDTO.getEmail());
        }
    }
}
