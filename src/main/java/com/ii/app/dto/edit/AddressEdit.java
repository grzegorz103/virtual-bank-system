package com.ii.app.dto.edit;

import com.ii.app.utils.validators.PostCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressEdit {
    private Long id;

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
    @Size(min = 7, max = 15)
    private String phoneNumber;

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    private String surname;

    @Past
    @NotNull
    private Instant dateOfBirth;
}
