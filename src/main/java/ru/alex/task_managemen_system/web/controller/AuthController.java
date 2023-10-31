package ru.alex.task_managemen_system.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.task_managemen_system.model.dto.user.LoginDTO;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.response.JwtResponse;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UserRepository;
import ru.alex.task_managemen_system.service.impl.DefaultAuthService;
import ru.alex.task_managemen_system.service.impl.DefaultUserService;
import ru.alex.task_managemen_system.util.exception.AccessDeniedException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final DefaultAuthService authService;

    private final DefaultUserService userService;

    private final UserRepository userRepository;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody @Valid final LoginDTO loginDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new AccessDeniedException("Login or password a is invalid");
        }
        return ResponseEntity
                .accepted()
                .body(authService.login(loginDTO));
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody final String refreshToken) {
        return ResponseEntity.ok().body(authService.refresh(refreshToken));
    }

    @PostMapping("/registration")
    public ResponseEntity<User> registration(@RequestBody @Valid UserDTO registrationDTO, BindingResult bindingResult) throws IllegalAccessException {
       User user = userService.save(registrationDTO, bindingResult);
       return ResponseEntity.ok().body(user);
    }

    @GetMapping("/")
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
