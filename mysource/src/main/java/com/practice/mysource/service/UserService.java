package com.practice.mysource.service;

import com.practice.mysource.entity.Role;

public interface UserService {
    void createUser(String firstName, String lastName, String email, String password);
    Role getRoleName(String name);
}
