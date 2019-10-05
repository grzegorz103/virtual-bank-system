package com.ii.app.repositories;

import com.ii.app.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>
{
        Optional<User> findByIdentifier( String identifier);
}
