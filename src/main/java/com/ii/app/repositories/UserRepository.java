package com.ii.app.repositories;

import com.ii.app.models.user.User;
import com.ii.app.models.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import static org.hibernate.loader.Loader.SELECT;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdentifier(String identifier);

    @Query("SELECT u " +
        "FROM User u " +
        "JOIN u.userRoles ur " +
        "WHERE ur.userType = :userType")
    List<User> findAllByUserType(@Param("userType") UserRole.UserType userType);

    @Query("SELECT u " +
        "FROM User u " +
        "JOIN u.userRoles ur " +
        "WHERE ur.userType = :userType " +
        "AND u.enabled = false")
    List<User> findAllByUserTypeAndNotEnabled(@Param("userType") UserRole.UserType userType);

    boolean existsByEmail(String email);

    boolean existsByIdentifier(String identifier);
}
