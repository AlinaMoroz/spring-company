package org.example.database.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.example.database.entity.Role;
import org.example.database.entity.User;
import org.example.database.pool.ConnectionPool;
import org.example.dto.PersonalInfo;
import org.example.dto.PersonalInfo2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>,FilterUserRepository,
        RevisionRepository<User,Long, Integer> {
    //HQl запрос
    // только для select
    @Query("select u from User u " +
            "where u.firstname like %:firstname% and u.lastname like %:lastname%")
//    @Query("select u from User u where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User>findAllByFirstnameContainingAndLastnameContaining(String firstname, String lastname);

    // SQL запрос
    // только для select
    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",

            nativeQuery = true)
    List<User>findAllByUsername(String username);

    @Modifying(clearAutomatically = true,flushAutomatically = true)//позволяет делать все операции ddl
    //очень полезные настройки, что бы данные не потерялись
    @Query("update User u set u.role = :role where u.id in (:ids)")
    int updateRole(Role role,Long... ids);

    Optional<User>findTopByOrderByIdDesc();

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50"))
    @Lock(LockModeType.PESSIMISTIC_READ)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDay, Sort sort);

    //Collection, Stream
    //Streamable, Slice, Page
// этот способ требует доп аннотации над сущьностью
//    @EntityGraph("User.company")
    //другой способ, указываем название полей с сущьности
    @EntityGraph(attributePaths = {"company", "company.locales"})//но есть врероятность, что не корект отраб Pagable
    @Query(value = "select u from User u",
    countQuery = "select count (distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);


   // List<PersonalInfo>findAllByCompanyId(Integer companyId);
    <T>List<T>findAllByCompanyId(Integer companyId, Class<T> clazz);
//    @Query(value = "SELECT firstname,"+
//            "lastname,"+
//            "birth_date,"+
//            " FROM users WHERE company_id = :companyId",
//            nativeQuery = true)
//    List<PersonalInfo2>findAllByCompanyId(Integer companyId);

}
