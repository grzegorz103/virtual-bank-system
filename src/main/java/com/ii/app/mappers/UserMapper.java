package com.ii.app.mappers;

import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import com.ii.app.models.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface UserMapper {
    User userInToUser(UserIn userIn);
    UserOut userToUserOut(User user);
}
