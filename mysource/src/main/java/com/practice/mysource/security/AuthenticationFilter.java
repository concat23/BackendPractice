package com.practice.mysource.security;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.mysource.dtorequest.LoginRequest;
import com.practice.mysource.dtorequest.UserRequest;
import com.practice.mysource.entity.User;
import com.practice.mysource.service.JwtService;
import com.practice.mysource.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE;
import static com.practice.mysource.domain.ApiAuthentication.unauthenticated;
import static com.practice.mysource.enumeration.LoginType.LOGIN_ATTEMPT;
import static com.practice.mysource.utils.RequestUtils.handleErrorResponse;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final UserService userService;
    private final JwtService jwtService;
    protected AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        super(new AntPathRequestMatcher("/user/login",POST.name()), authenticationManager);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        try {
            LoginRequest user = new ObjectMapper().configure(AUTO_CLOSE_SOURCE, true).readValue(request.getInputStream(), LoginRequest.class);
            userService.updateLoginAttempt(user.getEmail(), LOGIN_ATTEMPT);
            var authentication = unauthenticated(user.getEmail(), user.getPassword());
            return getAuthenticationManager().authenticate(authentication);
        }catch (Exception exc){
            log.error(exc.getMessage());
            handleErrorResponse(request,response,exc);
            return null;
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
