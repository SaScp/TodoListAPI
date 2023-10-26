package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.UserDTO;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UseRepository;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UseRepository useRepository;
    private final ModelMapper modelMapper;
    public String save(UserDTO userDTO) {

        User user = convertUserDtoToUser(userDTO);
        String uuid = UUID.randomUUID().toString();

        user.setUuid(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));

        user.setCreateAt(ZonedDateTime.now());
        user.setUpdateAt(ZonedDateTime.now());

        return uuid;
    }

    private User convertUserDtoToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


}
