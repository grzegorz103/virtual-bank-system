package com.ii.app.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressEdit
{
        private String city;

        private String street;

        private String postCode;

        private String houseNumber;

        private String phoneNumber;
}
