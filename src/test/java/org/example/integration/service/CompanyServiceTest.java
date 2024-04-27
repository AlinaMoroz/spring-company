package org.example.integration.service;

import org.example.database.entity.Company;
import org.example.database.repository.CompanyRepository;
import org.example.dto.CompanyReadDto;
import org.example.listner.entity.EntityEvent;
import org.example.service.CompanyService;
import org.example.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    private static final Integer companyId = 1;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Company(companyId, null, Collections.emptyMap())))
                .when(companyRepository).findById(companyId);

        var actualResult = companyService.findById(companyId);

        Assertions.assertTrue(actualResult.isPresent());
        var expectedResult = new CompanyReadDto(companyId,null);

        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

        Mockito.verify(eventPublisher).publishEvent(Mockito.any(EntityEvent.class));
        Mockito.verifyNoMoreInteractions(eventPublisher,userService);


    }
}