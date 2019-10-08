package com.ii.app.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    public enum UserType {
        ROLE_EMPLOYEE,
        ROLE_USER,
        ROLE_ADMIN
    }
}
