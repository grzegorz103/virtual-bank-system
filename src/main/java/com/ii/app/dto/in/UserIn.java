package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserIn {
    private String password;

    private String confirmPassword;

    private String email;

    private AddressIn address;
}
