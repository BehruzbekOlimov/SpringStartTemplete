package com.uzhan.clientinfobase.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uzhan.clientinfobase.entity.enums.Role;
import com.uzhan.clientinfobase.entity.template.RootEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Table(name = "users")
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User extends RootEntity implements UserDetails {

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, unique = true, length = 32)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(grantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
