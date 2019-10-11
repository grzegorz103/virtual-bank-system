package com.ii.app.dto.out;

import com.ii.app.dto.in.AddressIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserOut {
    private Long ID;

    private String identifier;

    private String email;

    private AddressOut address;

    private boolean locked;
}
