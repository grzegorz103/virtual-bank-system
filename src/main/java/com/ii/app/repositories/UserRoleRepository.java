package com.ii.app.repositories;

import com.ii.app.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>
{
}
