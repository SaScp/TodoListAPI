package ru.alex.task_managemen_system.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex.task_managemen_system.service.UserService;
import ru.alex.task_managemen_system.service.logger.DefaultSenderLog;
import ru.alex.task_managemen_system.service.update.update_user.UpdateComponent;
import ru.alex.task_managemen_system.service.update.update_user.UpdateEmail;
import ru.alex.task_managemen_system.service.update.update_user.UpdateName;
import ru.alex.task_managemen_system.service.update.update_user.UpdatePassword;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.dto.user.UpdateDTO;
import ru.alex.task_managemen_system.model.user.Role;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.util.exception.UserNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final DefaultSenderLog senderLogger;

    public CompletableFuture<User> save(final UserDTO userDTO) {
        User user = convertUserDtoToUser(userDTO);

        user.setUuid(UUID.randomUUID().toString());
        user.setRoles(Role.ROLE_USER);

        user.setCreateAt(ZonedDateTime.now());
        user.setUpdateAt(ZonedDateTime.now());

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        senderLogger.execute(
                this.getClass().getName() + " : " +
                        "save User:" + user.getUuid(), false);

        return CompletableFuture.completedFuture(user);
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

        user.setUpdateAt(ZonedDateTime.now());
        userRepository.save(user);
        senderLogger.execute(
                this.getClass().getName() + " : " +
                        "save update:" + user.getUuid(), false);
        return user;
    }


    public User getUserByUUID(String uuid) {
        return userRepository.findById(uuid).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    private User convertUserDtoToUser(UserDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }
}
