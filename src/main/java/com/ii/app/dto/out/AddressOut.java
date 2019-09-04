package com.ii.app.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressOut
{
        private String city;

        private String street;

        private String postCode;

        private String houseNumber;

        private String name;

        private String surname;

        private String phoneNumber;
}
