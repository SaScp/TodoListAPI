package ru.alex.task_managemen_system.service;

import ru.alex.task_managemen_system.model.dto.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.Token;

public interface UserService {

    public void update(UpdateDTO updateDTO, String uuid);

    public Token getUserByUUID(String uuid);

    public void save(Token userDTO);
}
