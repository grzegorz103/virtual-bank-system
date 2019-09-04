package com.ii.app.dto.out;

import com.ii.app.dto.in.AddressIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserOut
{
        private String username;

        private String email;

        private AddressOut address;
}
