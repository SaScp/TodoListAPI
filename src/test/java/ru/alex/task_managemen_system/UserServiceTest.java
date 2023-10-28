package ru.alex.task_managemen_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alex.task_managemen_system.model.dto.UserDTO;
import ru.alex.task_managemen_system.service.impl.DefaultUserService;

@AutoConfigureMockMvc
public class UserServiceTest {

    @MockBean
    private final DefaultUserService userService;

    public UserServiceTest(DefaultUserService userService) {
        this.userService = userService;
    }

    @Test
    public void test_function_save_userService() {
        userService.save(new UserDTO());
    }
}
