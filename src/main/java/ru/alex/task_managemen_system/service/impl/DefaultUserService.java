package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.model.dto.RegistrationDTO;
import ru.alex.task_managemen_system.model.dto.UpdateDTO;
import ru.alex.task_managemen_system.model.dto.Token;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.service.update.UpdateComponent;
import ru.alex.task_managemen_system.service.update.UpdateEmail;
import ru.alex.task_managemen_system.service.update.UpdateName;
import ru.alex.task_managemen_system.service.update.UpdatePassword;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service("defaultUserServiceBean")
@RequiredArgsConstructor
public class DefaultUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public void save(RegistrationDTO userDTO) {

        User user = convertregistrationDtoToUser(userDTO);
        String uuid = UUID.randomUUID().toString();

        user.setUuid(UUID.randomUUID().toString());
        user.setRoles(Collections.singleton(Role.USER));

        user.setCreateAt(ZonedDateTime.now());
        user.setUpdateAt(ZonedDateTime.now());

        userRepository.save(user);
    }

    public void update(UpdateDTO updateDTO, String uuid) {
        List<UpdateComponent> updateComponents = List.of(
                new UpdateName(),
                new UpdateEmail(),
                new UpdatePassword());

        User user = userRepository.getReferenceById(uuid);
        for (var i : updateComponents) {
            i.execute(updateDTO, user);
        }
        userRepository.save(user);
    }

    public User getUserByUUID(String uuid) {
        return userRepository.getReferenceById(uuid);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    private User convertregistrationDtoToUser(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }
}
