package com.ii.app.controllers;

import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import com.ii.app.models.user.JwtToken;
import com.ii.app.models.user.UserRole;
import com.ii.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserOut create(@RequestBody UserIn userIn) {
        return userService.create(userIn);
    }

    @GetMapping("/{type}")
    public List<UserOut> findByUserType(@PathVariable("type") UserRole.UserType userType) {
        return userService.findAllByUserType(userType);
    }
}
