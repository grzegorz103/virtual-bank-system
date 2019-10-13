package com.ii.app.services.interfaces;

import com.ii.app.dto.edit.PasswordEdit;
import com.ii.app.dto.edit.UserEdit;
import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import com.ii.app.models.user.JwtToken;
import com.ii.app.models.user.UserRole;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserOut create(UserIn userIn);

    UserOut findCurrentUser();

    List<UserOut> findAllByUserType(UserRole.UserType userType);

    UserOut findById(Long id);

    UserOut update(Long id, UserEdit userEdit);

    UserOut createEmployee(UserIn userIn);

    UserOut changeStatus(Long id);

    UserOut findByIdentifier(String identifier);

    List<UserOut> findAllByUserTypeAndNotEnabled(UserRole.UserType userType);

    UserOut changeEnableStatus(Long id);

    UserOut updatePassword(PasswordEdit passwordEdit);
}
