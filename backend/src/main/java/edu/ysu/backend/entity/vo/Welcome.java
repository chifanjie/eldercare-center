package edu.ysu.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Welcome {
    private String username;
    private String password;
    private String rePassword;
    private String email;
    private String vailCode;
    private String code;
    private String captcha;
}
