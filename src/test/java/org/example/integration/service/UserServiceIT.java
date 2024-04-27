package org.example.integration.service;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.database.entity.Role;
import org.example.database.pool.ConnectionPool;
import org.example.dto.UserCreateEditDto;
import org.example.dto.UserReadDto;
import org.example.integration.IntegrationTestBase;
import org.example.integration.annotation.IT;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_ID_NAME_IVAN = 1L;
    private static final Integer COMPANY_1 = 1;

    private final UserService userService;


    @Test
    void findAll(){
        var result = userService.findAll();
        Assertions.assertThat(result).hasSize(5);
    }
    @Test
    void findById(){
        Optional<UserReadDto> maybeUser = userService.findById(USER_ID_NAME_IVAN);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1
        );
        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
        assertEquals(userDto.getLastname(), actualResult.getLastname());
        assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                LocalDate.now(),
                "Test",
                "Test",
                Role.ADMIN,
                COMPANY_1
        );

        Optional<UserReadDto> actualResult = userService.update(USER_ID_NAME_IVAN, userDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDto.getUsername(), user.getUsername());
            assertEquals(userDto.getBirthDate(), user.getBirthDate());
            assertEquals(userDto.getFirstname(), user.getFirstname());
            assertEquals(userDto.getLastname(), user.getLastname());
            assertEquals(userDto.getCompanyId(), user.getCompany().id());
            assertSame(userDto.getRole(), user.getRole());
        });
    }

    @Test
    void delete() {
        assertFalse(userService.delete(-124L));
        assertTrue(userService.delete(USER_ID_NAME_IVAN));
    }



}
