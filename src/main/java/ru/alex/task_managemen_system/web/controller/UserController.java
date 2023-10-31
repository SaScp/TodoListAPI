package ru.alex.task_managemen_system.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.UserDTO;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.service.impl.DefaultUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final DefaultUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id) {

        return ResponseEntity
                .ok()
                .body(userService.getUserByUUID(id));
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<User> update(@PathVariable("id") String id, UpdateDTO updateDTO) {
        return ResponseEntity
                .ok()
                .body(userService.update(updateDTO, id));
    }

}
