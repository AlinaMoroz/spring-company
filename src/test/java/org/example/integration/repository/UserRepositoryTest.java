package org.example.integration.repository;

import lombok.RequiredArgsConstructor;
import org.example.database.entity.Role;
import org.example.database.entity.User;
import org.example.integration.IntegrationTestBase;
import org.example.database.repository.UserRepository;
import org.example.dto.PersonalInfo;
import org.example.dto.UserFilter;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkBatch(){
        var users = userRepository.findAll();
        userRepository.updateCompanyAndRole(users);
    }

    @Test
    void checkJdbcTemplate(){
        var users = userRepository.findAllByCompanyIdAndRole(1, Role.USER);
        assertThat(users).hasSize(1);
    }

    @Test
    void checkAuditing(){
        var ivan = userRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1l));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void checkCustomImplementation(){
        UserFilter filter = new UserFilter(null,"%ov%",LocalDate.now());
        var pageable = PageRequest.of(1, 2, Sort.by("id"));
        var users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
    }

    @Test
    void checkProjections(){
        var users = userRepository.findAllByCompanyId(1, PersonalInfo.class);
        assertThat(users).hasSize(2);
    }

    @Test
    void checkPageable(){
        // 2-я страничка размера 2
        // очень часто используется Slice наследник Pagable
        var pageable = PageRequest.of(1, 2, Sort.by("id"));
        var slice = userRepository.findAllBy(pageable);
        slice.forEach(user -> System.out.println(user.getCompany().getName()));
        //Работа как с итератором, но есть автоматический переход на новую страничку
        while (slice.hasNext()){
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user -> System.out.println(user.getCompany().getName()));

        }
    }

//Sort
    @Test
    void checkSort(){
        var sortBy = Sort.sort(User.class);
        var sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));

        var allUsers = userRepository.findTop3ByBirthDateBefore(LocalDate.now(),sort) ;
        assertThat(allUsers).hasSize(3);

    }



    @Test
    void findAllByFirstnameContainingAndLastnameContaining() {
        var users = userRepository.findAllByFirstnameContainingAndLastnameContaining("a", "ov");
        assertThat(users).hasSize(3);
    }

    @Test
    void checkUpdate(){
        var ivan = userRepository.getReferenceById(1l);
        assertSame(Role.ADMIN, ivan.getRole());
        var resultCount = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2,resultCount);
        var theSameIvan = userRepository.getReferenceById(1l);
        assertSame(Role.USER, theSameIvan.getRole());
    }

    @Test
    void checkFirstTop(){


        var topUser = userRepository.findTopByOrderByIdDesc();
        assertTrue(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }
}