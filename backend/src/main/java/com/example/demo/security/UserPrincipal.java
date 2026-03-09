package com.example.demo.security;

import com.example.demo.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record UserPrincipal(Long id, String email, String password) implements UserDetails {
    public static UserPrincipal build(User user) {
        return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword());
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.emptyList(); }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return email; } // Use email as the username
    public Long getId() { return id; }
}