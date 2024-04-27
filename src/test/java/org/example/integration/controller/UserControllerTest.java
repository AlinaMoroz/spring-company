package org.example.integration.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserCreateEditDto;
import org.example.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.example.dto.UserCreateEditDto.Fields.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
@AutoConfigureMockMvc//для имитации http запросов в наше приложение
class UserControllerTest extends IntegrationTestBase {

    private final MockMvc mockMvc;
    //позволяет отправлять http запросы

    @Test
    void findAll() throws Exception {
        //пытаемся симитировать запрос
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users",hasSize(5)));

    }

//    @Test
//    void findById() {
//    }
//
    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .param(username,"test@gmail.com")
                        .param(firstname, "test")
                        .param(lastname, "test")
                        .param(role, "ADMIN")
                        .param(companyId,"1")
                        .param(birthDate, "2000-01-01")
                        )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );

    }
//
//    @Test
//    void update() {
//    }
//
//    @Test
//    void delete() {
//    }
}