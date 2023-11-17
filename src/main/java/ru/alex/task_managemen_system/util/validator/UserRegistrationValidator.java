package ru.alex.task_managemen_system.util.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alex.task_managemen_system.model.dto.user.UserDTO;
import ru.alex.task_managemen_system.model.user.User;
import ru.alex.task_managemen_system.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserRegistrationValidator implements Validator {

   private final UserRepository userRepository;
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO user = (UserDTO) target;
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email","404", "user with email is create");
        }
    }
}
