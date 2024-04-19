package com.practice.mysource.init;

import com.practice.mysource.domain.RequestContext;
import com.practice.mysource.entity.Role;
import com.practice.mysource.enumeration.Authority;
import com.practice.mysource.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class InMemoryRole {

    private final RoleRepository roleRepository;
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            RequestContext.setUserId(0L);

            List<Authority> authorities = Arrays.asList(Authority.USER, Authority.ADMIN);
            authorities.forEach(authority -> saveRoleIfNotExists(roleRepository, authority));
            addRole(roleRepository, "User", Authority.USER);
            addRole(roleRepository, "Manager", Authority.MANAGER);
            addRole(roleRepository, "Admin", Authority.ADMIN);
            addRole(roleRepository, "Host", Authority.SUPER_ADMIN);
            RequestContext.start();
        };
    }

    private void saveRoleIfNotExists(RoleRepository roleRepository, Authority authority) {
        String roleName = authority.name();

        Optional<Role> existingRoleOptional = roleRepository.findByNameIgnoreCase(roleName);
        if (existingRoleOptional.isEmpty()) {

            Role role = new Role();
            role.setName(roleName);
            role.setAuthorities(String.valueOf(authority));
            roleRepository.save(role);
        }
    }

    private void addRole(RoleRepository roleRepository, String roleName, Authority authority) {

        Optional<Role> existingRoleOptional = roleRepository.findByNameIgnoreCase(roleName);
        if (existingRoleOptional.isEmpty()) {

            Role role = new Role();
            role.setName(roleName);
            role.setAuthorities(String.valueOf(authority));
            roleRepository.save(role);
        }
    }


}
