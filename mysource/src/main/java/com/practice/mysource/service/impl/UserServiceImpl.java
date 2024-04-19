package com.practice.mysource.service.impl;

import com.practice.mysource.entity.Confirmation;
import com.practice.mysource.entity.Credential;
import com.practice.mysource.entity.Role;
import com.practice.mysource.entity.User;
import com.practice.mysource.enumeration.Authority;
import com.practice.mysource.enumeration.EventType;
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
    public Role getRoleName(String name) {
        Optional<Role> role = this.roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow( () -> new ApiException("Role not found"));
    }


    private User createNewUser(String firstName,String lastName,String email){
        Role role = getRoleName(Authority.USER.name());
        return addUser(firstName,lastName,email,role);
    }
}
