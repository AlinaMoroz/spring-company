package org.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.example.database.entity.Role;
import org.example.validation.UserInfo;
import org.example.validation.group.CreateAction;

import java.time.LocalDate;

@Value
@FieldNameConstants//???
@UserInfo(groups = CreateAction.class)
public class UserCreateEditDto {
    @Email
    String username;
    LocalDate birthDate;
    @Size(min = 3, max = 64)
    String firstname;
    String lastname;
    Role role;
    Integer companyId;
}
