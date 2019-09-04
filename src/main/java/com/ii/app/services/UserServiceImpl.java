package com.ii.app.services;

import com.ii.app.dto.in.UserIn;
import com.ii.app.dto.out.UserOut;
import com.ii.app.mappers.UserMapper;
import com.ii.app.models.user.User;
import com.ii.app.models.user.UserRole;
import com.ii.app.repositories.UserRepository;
import com.ii.app.repositories.UserRoleRepository;
import com.ii.app.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService
{
        private final UserRepository userRepository;

        private final UserRoleRepository userRoleRepository;

        private final UserMapper userMapper;

        private final BCryptPasswordEncoder encoder;

        @Autowired
        public UserServiceImpl ( UserRepository userRepository,
                                 UserRoleRepository userRoleRepository,
                                 UserMapper userMapper,
                                 BCryptPasswordEncoder encoder )
        {
                this.userRepository = userRepository;
                this.userRoleRepository = userRoleRepository;
                this.userMapper = userMapper;
                this.encoder = encoder;
        }

        @Override
        public UserOut create ( UserIn userIn )
        {
                User mapped = userMapper.DTOtoEntity( userIn );
                mapped.setLocked( false );
                mapped.setCredentials( false );
                mapped.setEnabled( true );
                mapped.setUserRoles( Collections.singleton( userRoleRepository.findByUserType( UserRole.UserType.ROLE_USER ) ) );
                mapped.setPassword( encoder.encode( userIn.getPassword() ) );

                return userMapper.entityToDTO( userRepository.save( mapped ) );
        }

        @Override
        public UserOut getByUsername ( String username )
        {
                return userRepository.findByUsername( username )
                        .map( userMapper::entityToDTO )
                        .orElseThrow( () -> new UsernameNotFoundException( "Not found" ) );
        }

        @Override
        public boolean isLoginCorrect ( String login, String password )
        {
                User u = userRepository.findByUsername( login )
                        .orElseThrow( () -> new UsernameNotFoundException( "Not found" ) );

                return u.getUsername().equals( login )
                        && encoder.matches( password, u.getPassword() );
        }

        @Override
        public UserDetails loadUserByUsername ( String s ) throws UsernameNotFoundException
        {
                return userRepository.findByUsername( s )
                        .orElseThrow( () -> new UsernameNotFoundException( "Not found" ) );
        }
}
