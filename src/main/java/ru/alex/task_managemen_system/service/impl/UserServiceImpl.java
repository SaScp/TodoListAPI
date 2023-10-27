package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.UserDTO;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UseRepository;
import ru.alex.task_managemen_system.service.update.UpdateComponent;
import ru.alex.task_managemen_system.service.update.UpdateEmail;
import ru.alex.task_managemen_system.service.update.UpdateName;
import ru.alex.task_managemen_system.service.update.UpdatePassword;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UseRepository userRepository;
    private final ModelMapper modelMapper;
    public void save(UserDTO userDTO) {

        User user = convertUserDtoToUser(userDTO);
        String uuid = UUID.randomUUID().toString();

        user.setUuid(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));

        user.setCreateAt(ZonedDateTime.now());
        user.setUpdateAt(ZonedDateTime.now());

    }

    public void update(UpdateDTO updateDTO,  String uuid) {
        List<UpdateComponent> updateComponents = List.of(
                new UpdateName(),
                new UpdateEmail(),
                new UpdatePassword());

        User user = userRepository.getReferenceById(uuid);
        for (var i : updateComponents) {
            i.execute(updateDTO, user);
        }
    }
    private User convertUserDtoToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


}
