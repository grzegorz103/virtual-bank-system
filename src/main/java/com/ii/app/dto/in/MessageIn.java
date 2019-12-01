package com.ii.app.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageIn {
    private Long id;

    @NotEmpty
    @Length(min = 1, max = 255)
    private String message;

    private Instant date;

    @Positive
    private Long conversationId;
}
