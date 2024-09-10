package com.ing.brokercore.utils.validators;

import com.ing.brokercore.utils.Constants;
import com.ing.brokercore.utils.annotations.ValidCurrency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        // ONLY TRY
        return value.equalsIgnoreCase(Constants.TRY);
    }
}
