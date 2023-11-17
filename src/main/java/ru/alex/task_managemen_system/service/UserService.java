package ru.alex.task_managemen_system.service;

import org.springframework.scheduling.annotation.Async;
import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.user.User;

import java.util.concurrent.CompletableFuture;


public interface UserService {

    User update(UpdateDTO updateDTO, String uuid);

    User getUserByUUID(String uuid);

     User getUserByEmail(String email);

    @Async
     CompletableFuture<User> save(UserDTO userDTO);
}
