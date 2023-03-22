package com.jobis.config.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails extends User {

    private static final String USER = "USER";

    public CustomUserDetails(String username, String password) {
        super(username, password, Collections.singletonList(new SimpleGrantedAuthority(USER)));
    }

}
