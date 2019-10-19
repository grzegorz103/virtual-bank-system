package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserIn {
    @Length(min = 8, max = 100)
    @NotNull
    private String password;

    private String confirmPassword;

    private String email;

    private AddressIn address;
}
