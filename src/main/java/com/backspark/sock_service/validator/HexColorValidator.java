package com.backspark.sock_service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HexColorValidator implements ConstraintValidator<ValidHexColor, String> {

    private static final String HEX_COLOR_PATTERN = "^#([A-Fa-f0-9]{6})$";

    @Override
    public boolean isValid(String colorHex, ConstraintValidatorContext context) {
        if (colorHex == null) {
            return true;
        }
        return colorHex.matches(HEX_COLOR_PATTERN);
    }
}
