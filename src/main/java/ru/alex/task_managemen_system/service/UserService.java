package ru.alex.task_managemen_system.service;

import org.springframework.scheduling.annotation.Async;
import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.user.User;

import java.util.concurrent.CompletableFuture;


public interface UserService {

    public User update(UpdateDTO updateDTO, String uuid);

    public User getUserByUUID(String uuid);

    public User getUserByEmail(String email);

    @Async
    public CompletableFuture<User> save(UserDTO userDTO);
}
