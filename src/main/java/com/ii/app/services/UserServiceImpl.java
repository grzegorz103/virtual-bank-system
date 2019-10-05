package com.ii.app.services;

import com.ii.app.config.security.JwtTokenGenerator;
import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import com.ii.app.mappers.UserMapper;
import com.ii.app.models.user.JwtToken;
import com.ii.app.models.user.User;
import com.ii.app.models.user.UserRole;
import com.ii.app.repositories.UserRepository;
import com.ii.app.repositories.UserRoleRepository;
import com.ii.app.services.interfaces.UserService;
import com.ii.app.utils.Constants;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    private final Constants CONSTANTS;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           UserMapper userMapper,
                           BCryptPasswordEncoder passwordEncoder,
                           Constants CONSTANTS) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.userMapper = userMapper;
        this.CONSTANTS = CONSTANTS;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserOut create(UserIn userIn) {
        User mapped = userMapper.userInToUser(userIn);
        mapped.setLocked(false);
        mapped.setCredentials(false);
        mapped.setEnabled(true);
        mapped.setIdentifier(RandomStringUtils.randomNumeric(CONSTANTS.USER_IDENTIFIER_LENGTH));
        mapped.setUserRoles(Collections.singleton(userRoleRepository.findByUserType(UserRole.UserType.ROLE_USER)));
        mapped.setPassword(passwordEncoder.encode(userIn.getPassword()));

        return userMapper.userToUserOut(userRepository.save(mapped));
    }

    @Override
    public UserOut getByUsername(String username) {
        return userRepository.findByIdentifier(username)
            .map(userMapper::userToUserOut)
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByIdentifier(s)
            .orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }
}
