package org.example.integration.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.database.entity.Company;
import org.example.integration.IntegrationTestBase;
import org.example.database.repository.CompanyRepository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor

class CompanyRepositoryTest extends IntegrationTestBase {

    public static final Integer AMAZONE_ID = 3;

    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;
//    private final TransactionTemplate transactionTemplate;

    @Test
    void checkFindByQueries(){
        companyRepository.findByName("Google");
        companyRepository.findAllByNameContainingIgnoreCase("a");
    }

    @Test
    void findById() {
        var company = entityManager.find(Company.class, 1);
        assertNotNull(company);
        Assertions.assertThat(company.getLocales()).hasSize(2);
    }
//
//    @Test
//    void delete() {
//        var maybeCompany = companyRepository.findById(AMAZONE_ID);
//        assertTrue(maybeCompany.isPresent());
//
//
//    }
}