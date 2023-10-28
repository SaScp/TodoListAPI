package ru.alex.task_managemen_system.service;

import ru.alex.task_managemen_system.model.dto.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.UserDTO;

public interface UserService {

    public void update(UpdateDTO updateDTO, String uuid);

    public UserDTO getUserByUUID(String uuid);

    public void save(UserDTO userDTO);
}
