package com.uzhan.clientinfobase.controller;

import com.uzhan.clientinfobase.payload.request.UserAuthRequest;
import com.uzhan.clientinfobase.payload.request.UserRegisterRequest;
import com.uzhan.clientinfobase.payload.response.UserWithJwtResponse;
import com.uzhan.clientinfobase.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    UserWithJwtResponse register(@Valid @RequestBody UserRegisterRequest req) {
        return authService.register(req);
    }

    @PostMapping("signIn")
    UserWithJwtResponse signIn(@Valid @RequestBody UserAuthRequest req) {
        return authService.auth(req);
    }

}
