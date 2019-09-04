package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressIn
{
        private String city;

        private String street;

        private String postCode;

        private String houseNumber;

        private String name;

        private String surname;

        private String phoneNumber;

}
