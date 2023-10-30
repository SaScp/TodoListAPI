package ru.alex.task_managemen_system.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
