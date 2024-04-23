package com.practice.mysource.service.impl;

import com.practice.mysource.domain.Token;
import com.practice.mysource.domain.TokenData;
import com.practice.mysource.dto.UserDTO;
import com.practice.mysource.enumeration.TokenType;
import com.practice.mysource.security.JwtConfiguration;
import com.practice.mysource.service.JwtService;
import com.practice.mysource.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.practice.mysource.constant.Constants.*;
import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static jdk.internal.joptsimple.internal.Strings.EMPTY;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl extends JwtConfiguration implements JwtService {

    private final UserService userService;
    private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecret()));
    private final Function<String, Claims> claimsFunction = token -> Jwts
                                            .parser()
                                            .verifyWith(key.get())
                                            .build()
                                            .parseSignedClaims(token)
                                            .getPayload();
    private final Function<String,String> subject = token -> getClaimsValue(token,Claims::getSubject);

    private final BiFunction<HttpServletRequest, String,Optional<String>> extractToken = (request, cookieName)
            -> Optional.of(stream(request.getCookies() == null ? new Cookie[]{new Cookie(EMPTY_VALUE, EMPTY_VALUE)} : request.getCookies())
            .filter(cookie -> Objects.equals(cookieName, cookie.getName()))
            .map(Cookie::getValue)
            .findAny()).orElse(empty());

    private final BiFunction<HttpServletRequest, String, Optional<Cookie>> extractCookie = (request, cookieName)
            -> Optional.ofNullable(request.getCookies())
            .flatMap(cookies -> Stream.of(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .map(Optional::of)
                    .findFirst()
                    .orElse(Optional.empty())
            );
    // 8:24:15
    private Function<String, List<GrantedAuthority>> authorities = token -> commaSeparatedStringToAuthorityList(new StringJoiner(AUTHORITY_DELIMITER)
            .add(claimsFunction.apply(token).get(AUTHORITIES, String.class))
            .add(ROLE_PREFIX + claimsFunction.apply(token).get(ROLE, String.class)).toString());


    @Override
    public String createToken(UserDTO userDTO, Function<Token, String> tokenStringFunction) {
        return null;
    }

    @Override
    public Optional<String> extractToken(HttpServletRequest request, UserDTO userDTO, TokenType tokeType) {
        return empty();
    }

    @Override
    public void addCookie(HttpServletResponse response, UserDTO userDTO, TokenType tokenType) {

    }

    @Override
    public <T> T getTokenData(String token, Function<TokenData, T> tokenDataTFunction) {
        return null;
    }
}
