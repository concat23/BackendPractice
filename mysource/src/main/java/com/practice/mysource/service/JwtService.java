package com.practice.mysource.service;

import com.practice.mysource.domain.Token;
import com.practice.mysource.domain.TokenData;
import com.practice.mysource.dto.UserDTO;
import com.practice.mysource.enumeration.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    String createToken(UserDTO userDTO, Function<Token, String> tokenStringFunction);
    Optional<String> extractToken(HttpServletRequest request,UserDTO userDTO, TokenType tokeType);
    void addCookie(HttpServletResponse response, UserDTO userDTO, TokenType tokenType);
    <T> T getTokenData(String token, Function<TokenData, T> tokenDataTFunction);
}
