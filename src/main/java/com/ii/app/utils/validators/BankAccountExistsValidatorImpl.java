package com.ii.app.utils.validators;

import com.ii.app.repositories.BankAccountRepository;
import com.ii.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class BankAccountExistsValidatorImpl implements ConstraintValidator<BankAccountExists, String> {

    private final BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountExistsValidatorImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    @Override
    public void initialize(BankAccountExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        value = value.replace(" ", "");
        return bankAccountRepository.existsByNumber(value);
    }
}
