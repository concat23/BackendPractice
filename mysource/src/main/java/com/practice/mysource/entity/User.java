package com.practice.mysource.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static jakarta.persistence.FetchType.EAGER;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@JsonInclude(NON_DEFAULT)
public class User extends Auditable{
    @Column(updatable = false,unique = true,nullable = false)
    private String userId;
    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    private Integer loginAttempts;

    private LocalDateTime lastLogin;
    private String phone;

    private String bio;
    private String imageUrl;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean enabled;

    private boolean mfa;

    @JsonIgnore
    private String qrCodeSecret;
    @Column(columnDefinition = "TEXT")
    private String qrCodeImageUri;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fkey_user_id")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fkey_role_id")
            )
    )
    private Role role;


}
