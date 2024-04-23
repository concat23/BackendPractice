package com.practice.mysource.domain;

import com.practice.mysource.dto.UserDTO;
import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Builder
@Getter
@Setter
public class TokenData {
    private UserDTO userDTO;
    private Claims claims;
    private boolean valid;
    private List<GrantedAuthority> authorities;
}
