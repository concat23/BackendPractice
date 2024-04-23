package com.practice.mysource.domain;

import com.practice.mysource.dto.UserDTO;
import com.practice.mysource.exception.ApiException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class ApiAuthentication extends AbstractAuthenticationToken {
    private static final String PASSWORD_PROTECTED = "[PASSWORD PROTECTED]";
    private static final String EMAIL_PROTECTED = "[EMAIL PROTECTED]";
    private UserDTO user;
    private String email;
    private String password;
    private boolean authenticated;

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new ApiException("You cannot set authentication");
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    public ApiAuthentication(String email, String password) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.email = email;
        this.password = password;
        this.authenticated = true;
    }

    public ApiAuthentication(UserDTO userDTO, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = userDTO;
        this.password = PASSWORD_PROTECTED;
        this.email = EMAIL_PROTECTED;
        this.authenticated = true;
    }

    public static ApiAuthentication unauthenticated(String email, String password){
        return new ApiAuthentication(email,password);
    }

    public static ApiAuthentication authenticated(UserDTO userDTO, Collection<? extends GrantedAuthority> authorities) {
       return new ApiAuthentication(userDTO,authorities);
    }
    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
