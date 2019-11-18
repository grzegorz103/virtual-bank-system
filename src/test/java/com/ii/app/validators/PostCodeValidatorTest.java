package com.ii.app.validators;

import com.ii.app.utils.validators.PostCodeValidatorImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class PostCodeValidatorTest {

    @Spy
    private PostCodeValidatorImpl postCodeValidator;

    @Test
    public void isValidTest() {
        assertTrue(postCodeValidator.isValid("11-111", null));
        assertTrue(postCodeValidator.isValid("99-999", null));
    }

    @Test
    public void isValidIncorrectTest() {
        assertFalse(postCodeValidator.isValid(null, null));
        assertFalse(postCodeValidator.isValid("11-11", null));
        assertFalse(postCodeValidator.isValid("11-1111", null));
        assertFalse(postCodeValidator.isValid("   ", null));
    }
}
