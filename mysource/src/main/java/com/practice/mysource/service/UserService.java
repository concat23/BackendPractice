package com.practice.mysource.service;

import com.practice.mysource.entity.Role;
import com.practice.mysource.enumeration.LoginType;

public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    Role getRoleName(String name);

    void verifyAccountKey(String key);

    void updateLoginAttempt(String email, LoginType loginType);
}
