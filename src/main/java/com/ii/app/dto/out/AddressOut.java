package com.ii.app.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressOut {
    private Long id;

    private String city;

    private String street;

    private String postCode;

    private String houseNumber;

    private String name;

    private String surname;

    private String phoneNumber;

    private Instant dateOfBirth;
}
