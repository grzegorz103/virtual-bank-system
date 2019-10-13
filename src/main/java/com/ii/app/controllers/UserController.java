package com.ii.app.controllers;

import com.ii.app.dto.edit.PasswordEdit;
import com.ii.app.dto.edit.UserEdit;
import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import com.ii.app.models.user.JwtToken;
import com.ii.app.models.user.UserRole;
import com.ii.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("/create/employee")
    @Secured("ROLE_ADMIN")
    public UserOut createEmployee(@RequestBody UserIn userIn) {
        return userService.createEmployee(userIn);
    }

    @GetMapping("/type/{type}")
    public List<UserOut> findByUserType(@PathVariable("type") UserRole.UserType userType,
                                        @RequestParam("disabledOnly")  Optional<String> disabledOnly ){
      if(disabledOnly.isPresent()){
          return userService.findAllByUserTypeAndNotEnabled(userType);
      }else{
          return userService.findAllByUserType(userType);
      }
    }

    @GetMapping("/{id}")
    public UserOut findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public UserOut update(@PathVariable("id") Long id,
                          @RequestBody UserEdit userEdit) {
        return userService.update(id, userEdit);
    }

    @GetMapping("/byIdentifier/{identifier}")
    public UserOut findByIdentifier(@PathVariable("identifier") String identifier) {
        return userService.findByIdentifier(identifier);
    }

    @PatchMapping("/{id}/status")
    public UserOut changeLockStatus(@PathVariable("id") Long id) {
        return userService.changeStatus(id);
    }

    @PatchMapping("/{id}/activate")
    public UserOut changeEnableStatus(@PathVariable("id") Long id){
        return userService.changeEnableStatus(id);
    }

    @GetMapping("/auth/current")
    @PreAuthorize("isAuthenticated()")
    public UserOut findCurrentUser(){
        return userService.findCurrentUser();
    }

    @PatchMapping("/password/edit")
    public UserOut updatePassword(@Valid @RequestBody PasswordEdit passwordEdit){
        return userService.updatePassword(passwordEdit);
    }
}
