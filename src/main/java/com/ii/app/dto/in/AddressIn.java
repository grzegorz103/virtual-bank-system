package com.ii.app.dto.in;

import com.ii.app.utils.validators.PostCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressIn {

    @NotBlank
    @Size(min = 3, max = 60)
    private String city;

    @NotBlank
    @Size(min = 3, max = 60)
    private String street;

    @PostCode
    private String postCode;

    @NotBlank
    @Size(min = 1, max = 20)
    private String houseNumber;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    private String surname;

    @NotBlank
    @Size(min = 7, max = 15)
    private String phoneNumber;

    @Past
    @NotNull
    private Instant dateOfBirth;
}
