package com.ii.app.validators;

import com.ii.app.repositories.UserRepository;
import com.ii.app.utils.validators.EmailTakenValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmailTakenValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private EmailTakenValidatorImpl validator;

    @Test
    public void isValidTest() {
        when(userRepository.existsByEmail(any(String.class))).thenReturn(false);
        assertTrue(validator.isValid("test@test.com", null));
        assertTrue(validator.isValid("test2@test2.com", null));
    }

    @Test
    public void isValidTakenEmailTest() {
        when(userRepository.existsByEmail(any(String.class))).thenReturn(true);
        assertFalse(validator.isValid("test@test.com", null));
        assertFalse(validator.isValid(null, null));
    }

}
