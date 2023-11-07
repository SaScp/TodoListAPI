package ru.alex.task_managemen_system.web.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.service.AdminService;
import ru.alex.task_managemen_system.service.impl.DefaultAdminService;
import ru.alex.task_managemen_system.service.impl.DefaultAuthService;
import ru.alex.task_managemen_system.service.impl.DefaultUserService;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
@RequiredArgsConstructor
public class AdminController {

  /*  @Qualifier("defaultAdminService")
    private final AdminService adminService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(adminService.getUsers());
    }

    @PostMapping("/block/{id}")
    public ResponseEntity<HttpStatus> ban(@PathVariable String id) {
        return ResponseEntity
                .ok()
                .body(adminService.isBlockUser(id)? HttpStatus.ACCEPTED: HttpStatus.NOT_FOUND);
    }*/ //TODO
}
