package com.uzhan.clientinfobase.service;

import com.uzhan.clientinfobase.config.jwt.JwtUtils;
import com.uzhan.clientinfobase.entity.User;
import com.uzhan.clientinfobase.entity.enums.Role;
import com.uzhan.clientinfobase.payload.request.UserAuthRequest;
import com.uzhan.clientinfobase.payload.request.UserRegisterRequest;
import com.uzhan.clientinfobase.payload.response.UserWithJwtResponse;
import com.uzhan.clientinfobase.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!")
        );
    }

    public UserWithJwtResponse register(UserRegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setUsername(req.getUsername().trim());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        String jwt = JwtUtils.TOKEN_PREFIX + jwtUtils.generateToken(user.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return new UserWithJwtResponse(
                user,
                jwt
        );
    }

    public UserWithJwtResponse auth(UserAuthRequest req) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(req.getUsername().trim(), req.getPassword().trim());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        User user = userRepository.findByUsername(req.getUsername().trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String jwt = JwtUtils.TOKEN_PREFIX + jwtUtils.generateToken(user.getUsername());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        return new UserWithJwtResponse(
                user,
                jwt
        );
    }
}
