package org.example.apitest.model.auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class Login implements Serializable {
    private String loginNm;
    private String loginPw;
}
