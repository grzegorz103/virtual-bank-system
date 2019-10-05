package com.ii.app.models.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Getter
@Immutable
@AllArgsConstructor
public class JwtToken {
    private String token;

    private JwtToken(){}
}
