package com.practice.mysource.resource;

import com.practice.mysource.domain.Response;
import com.practice.mysource.dtorequest.UserRequest;
import com.practice.mysource.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static com.practice.mysource.utils.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/user"})
public class UserResource {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest user, HttpServletRequest request){
        this.userService.createUser(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
        return ResponseEntity
                .created(getUri())
                .body(getResponse(request,emptyMap(),"Account created. Check your email to enable your account", CREATED));
    }

    @GetMapping("/verify/account")
    public ResponseEntity<Response> verifyAccountKey(@RequestParam("key") String key, HttpServletRequest request){
        this.userService.verifyAccountKey(key);
        return ResponseEntity.ok().body(getResponse(request, emptyMap(), "Account verified",OK));
    }

    @PostMapping("/login")
    public ResponseEntity<?> test(@RequestBody UserRequest user){
        UsernamePasswordAuthenticationToken unauthenticated = UsernamePasswordAuthenticationToken.unauthenticated(user.getEmail(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(unauthenticated);
        return ResponseEntity.ok().body(Map.of("user", authenticate));
    }
    private URI getUri() {
        return URI.create("");
    }
}
