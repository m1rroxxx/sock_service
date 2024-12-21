package com.backspark.sock_service.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HexColorValidator.class)
public @interface ValidHexColor {
    String message() default "Invalid hex color format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
