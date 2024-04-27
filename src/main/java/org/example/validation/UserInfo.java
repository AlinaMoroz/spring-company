package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.validation.impl.UserInfoValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UserInfoValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UserInfo {
    String message() default "Firstname and Lastname should be filled";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
