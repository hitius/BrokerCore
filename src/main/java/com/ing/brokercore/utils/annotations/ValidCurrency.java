package com.ing.brokercore.utils.annotations;


import com.ing.brokercore.utils.validators.CurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CurrencyValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrency {

    String message() default "Invalid currency, only TRY is allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
