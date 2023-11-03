package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.alex.task_managemen_system.service.update.update_user.UpdateComponent;
import ru.alex.task_managemen_system.service.update.update_user.UpdateEmail;
import ru.alex.task_managemen_system.service.update.update_user.UpdateName;
import ru.alex.task_managemen_system.service.update.update_user.UpdatePassword;
import ru.alex.task_managemen_system.util.exception.RegistrationUserException;
import ru.alex.task_managemen_system.util.validator.UserRegistrationValidator;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefaultUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public User save(final UserDTO userDTO) {


        User user = convertregistrationDtoToUser(userDTO);


        user.setUuid(UUID.randomUUID().toString());
        user.setRoles(Role.USER);

        user.setCreateAt(ZonedDateTime.now());
        user.setUpdateAt(ZonedDateTime.now());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User update(final UpdateDTO updateDTO, String uuid) {
        List<UpdateComponent> updateComponents = List.of(
                new UpdateName(),
                new UpdateEmail(),
                new UpdatePassword());

        User user = userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);
        for (var i : updateComponents) {
            i.execute(updateDTO, user);
        }
        userRepository.save(user);
        return user;
    }

    public User getUserByUUID(String uuid) {
        return userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    private User convertregistrationDtoToUser(UserDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }
}
