package com.practice.mysource.dto;

import com.practice.mysource.entity.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private Long createdBy;
    private Long updatedBy;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String bio;
    private String imageUrl;
    private String qrCodeImageUri;
    private LocalDateTime lastLogin;
    private String createdAt;
    private String updatedAt;
    private String role;
    private String authorities;
    private Integer loginAttempts;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private boolean mfa;


}
