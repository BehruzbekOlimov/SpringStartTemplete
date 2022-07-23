package com.uzhan.clientinfobase.payload.response;

import com.uzhan.clientinfobase.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithJwtResponse {
    private User user;
    private String jwt;
}
