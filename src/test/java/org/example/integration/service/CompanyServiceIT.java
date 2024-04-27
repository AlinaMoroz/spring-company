package org.example.integration.service;


import lombok.RequiredArgsConstructor;
import org.example.Main;
import org.example.cofig.conditional.DatabaseProperties;
import org.example.database.entity.Company;
import org.example.dto.CompanyReadDto;
import org.example.integration.annotation.IT;
import org.example.listner.entity.EntityEvent;
import org.example.service.CompanyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = Main.class,
//initializers = ConfigDataApplicationContextInitializer.class)
//@SpringBootTest
//@ActiveProfiles("test")
@IT
@RequiredArgsConstructor
public class CompanyServiceIT {
    private static final Integer companyId = 1;

    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById(){


        var actualResult = companyService.findById(companyId);

        Assertions.assertTrue(actualResult.isPresent());
        var expectedResult = new CompanyReadDto(companyId,null);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));


    }
}
