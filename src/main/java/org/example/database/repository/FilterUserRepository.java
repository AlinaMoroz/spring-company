package org.example.database.repository;

import org.example.database.entity.Role;
import org.example.database.entity.User;
import org.example.dto.PersonalInfo;
import org.example.dto.UserFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilterUserRepository {
    List<User> findAllByFilter(UserFilter userFilter);

    List<PersonalInfo> findAllByCompanyIdAndRole(Integer companyId, Role role);

    void updateCompanyAndRole(List<User> users);

    void updateCompanyAndRoleNamed(List<User> users);
}
