package com.ii.app.dto.out;

import com.ii.app.models.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageOut {
    private Long id;
    private String message;
    private UserOut user;
    private Instant date;
}
