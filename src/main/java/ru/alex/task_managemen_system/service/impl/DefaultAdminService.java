package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.service.AdminService;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;
import ru.alex.task_managemen_system.web.controller.AdminController;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultAdminService implements AdminService {

    private final UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        log.info("get user success");
        return users;
    }

    @Override
    public Boolean isBlockUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            user.get().setRoles(Role.ROLE_BLOCK);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }


}
