package com.ii.app.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressEdit {
    private Long id;

    private String city;

    private String street;

    private String postCode;

    private String houseNumber;

    private String phoneNumber;

    private String name;

    private String surname;

    private Instant dateOfBirth;
}
