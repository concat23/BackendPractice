package com.practice.mysource.service.impl;

import com.practice.mysource.cache.CacheStore;
import com.practice.mysource.domain.RequestContext;
import com.practice.mysource.entity.Confirmation;
import com.practice.mysource.entity.Credential;
import com.practice.mysource.entity.Role;
import com.practice.mysource.entity.User;
import com.practice.mysource.enumeration.Authority;
import com.practice.mysource.enumeration.EventType;
import com.practice.mysource.enumeration.LoginType;
import com.practice.mysource.event.UserEvent;
import com.practice.mysource.exception.ApiException;
import com.practice.mysource.repository.ConfirmationRepository;
import com.practice.mysource.repository.CredentialRepository;
import com.practice.mysource.repository.RoleRepository;
import com.practice.mysource.repository.UserRepository;
import com.practice.mysource.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static com.practice.mysource.enumeration.EventType.REGISTRATION;
import static com.practice.mysource.utils.UserUtils.addUser;
import static java.time.LocalDateTime.now;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
//    private final BCryptPasswordEncoder encoder;
    private final CacheStore<String, Integer> userCache;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(String firstName, String lastName, String email, String password) {
        User newUser = this.userRepository.save(createNewUser(firstName,lastName,email));
        Credential newCredential = new Credential(newUser, password);
        this.credentialRepository.save(newCredential);
        Confirmation newConfirmation = new Confirmation(newUser);
        this.confirmationRepository.save(newConfirmation);
        this.publisher.publishEvent(new UserEvent(newUser, REGISTRATION, Map.of("key", newConfirmation.getKey())));

    }

    @Override
    public void verifyAccountKey(String key) {
        Confirmation confirmation = getUserConfirmation(key);
        User user = getUserByEmail(confirmation.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        confirmationRepository.delete(confirmation);

    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        User user = getUserByEmail(email);
        RequestContext.setUserId(user.getId());
        switch (loginType){
            case LOGIN_ATTEMPT -> {
                if (userCache.get(user.getEmail()) == null){
                    user.setLoginAttempts(0);
                    user.setAccountNonLocked(true);
                }
                user.setLoginAttempts(user.getLoginAttempts() + 1);
                userCache.put(user.getEmail(),user.getLoginAttempts());
                if (userCache.get(user.getEmail()) > 5){
                    user.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                user.setAccountNonLocked(true);
                user.setLoginAttempts(0);
                user.setLastLogin(now());
                userCache.evict(user.getEmail());
            }
        }

        userRepository.save(user);
    }

    @Override
    public Role getRoleName(String name) {
        Optional<Role> role = this.roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow( () -> new ApiException("Role not found"));
    }

    private User getUserByEmail(String email) {
        Optional<User> userByEmail = this.userRepository.findByEmailIgnoreCase(email);
        return userByEmail.orElseThrow(() -> new ApiException("User not found."));
    }

    private Confirmation getUserConfirmation(String key){
        return this.confirmationRepository.findByKey(key).orElseThrow(() -> new ApiException("Confirmation key not found"));
    }

    private User createNewUser(String firstName,String lastName,String email){
        Role role = getRoleName(Authority.USER.name());
        return addUser(firstName,lastName,email,role);
    }
}
