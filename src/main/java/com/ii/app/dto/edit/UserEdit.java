package com.ii.app.dto.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEdit {
    private Long id;
    private String identifier;
    private String email;
    private AddressEdit address;
}
