package com.limrainfracon.model;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // implement based on your requirements
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // implement based on your requirements
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // implement based on your requirements
        return true;
    }

    @Override
    public boolean isEnabled() {
        // implement based on your requirements
        return true;
    }

    public User getUser() {
        return user;
    }

    // additional getters and setters for your custom fields
}
