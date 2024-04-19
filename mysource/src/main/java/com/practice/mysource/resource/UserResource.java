package com.practice.mysource.resource;

import com.practice.mysource.domain.Response;
import com.practice.mysource.dtorequest.UserRequest;
import com.practice.mysource.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.practice.mysource.utils.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/user"})
public class UserResource {
    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Response> saveUser(@RequestBody @Valid UserRequest user, HttpServletRequest request){
        this.userService.createUser(user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword());
        return ResponseEntity
                .created(getUri())
                .body(getResponse(request,emptyMap(),"Account created. Check your email to enable your account", CREATED));
    }

    private URI getUri() {
        return URI.create("");
    }
}
