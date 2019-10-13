package com.ii.app.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertTrue;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordEdit {
    private String password;
    private String confirmPassword;

    @AssertTrue
    public boolean isPasswordsEqual() {
        return StringUtils.equals(password, confirmPassword);
    }
}
