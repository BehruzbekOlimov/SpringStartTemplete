package com.uzhan.clientinfobase.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    @Size(min = 2, max = 64, message = "Name size must be between 2 and 64")
    private String name;

    @NotBlank
    @Size(min = 4, max = 64, message = "Username size must be between 4 and 64")
    private String username;

    @NotBlank
    @Size(min = 6, message = "Password min size 6")
    private String password;
}
