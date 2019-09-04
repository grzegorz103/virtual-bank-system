package com.ii.app.services.interfaces;

import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService
{
        UserOut create ( UserIn userIn );

        UserOut getByUsername ( String username );

        boolean isLoginCorrect ( String login, String password );
}
