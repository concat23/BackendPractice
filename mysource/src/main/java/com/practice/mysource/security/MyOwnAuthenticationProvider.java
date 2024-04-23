package com.practice.mysource.security;

import com.practice.mysource.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MyOwnAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) authentication;
        UserDetails userFromDB = this.userDetailsService.loadUserByUsername((String) user.getPrincipal());
        String password = (String) user.getCredentials();
        if (password.equals(userFromDB.getPassword())){
            return UsernamePasswordAuthenticationToken.authenticated(userFromDB,"[PASSWORD PROTECTED]", userFromDB.getAuthorities());
        }
        throw new BadCredentialsException("Unable to login");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
