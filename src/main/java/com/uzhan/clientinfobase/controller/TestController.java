package com.uzhan.clientinfobase.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * master 8/4/2021
 *
 * @author User
 */
@RestController
@RequestMapping("/api/test/")
public class TestController {


    @GetMapping
    String HelloEverybody() {
        return "Hello Everybody";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/user")
    String HelloUser() {
        return "Hello User";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/admin")
    String HelloAdmin() {
        return "Hello Admin";
    }

}
