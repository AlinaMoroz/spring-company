package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.database.entity.Company;
import org.example.database.entity.User;
import org.example.database.repository.CompanyRepository;
import org.example.database.repository.UserRepository;
import org.example.dto.UserCreateEditDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User>{
    private final CompanyRepository companyRepository;

    @Override
    public User map(UserCreateEditDto object, User toObject) {
        copy(object,toObject);
        return toObject;
    }

    @Override
    public User map(UserCreateEditDto object) {


        User user = new User();
        copy(object, user);

        return user;
    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));
    }

    private Company getCompany(Integer companyId){
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById)
                .orElse(null);

    }
}
