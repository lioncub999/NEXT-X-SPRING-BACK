package org.example.apitest.model.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class AuthInfo {
    private String token;
}
