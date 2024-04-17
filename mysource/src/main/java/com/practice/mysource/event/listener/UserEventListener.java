package com.practice.mysource.event.listener;


import com.practice.mysource.event.UserEvent;
import com.practice.mysource.service.EmailService;
import jakarta.persistence.EntityListeners;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventListener {

    private final EmailService emailService;

    @EventListener
    public void onUserEvent(UserEvent  userEvent){
        switch (userEvent.getType()){
            case REGISTRATION -> emailService.sendNewAccountEmail(
                                                 userEvent.getUser().getFirstName(),
                                                 userEvent.getUser().getEmail(),
                                                 (String) userEvent.getData().get("key"));
            case RESETPASSWORD -> emailService.sendPasswordResetEmail(
                                                 userEvent.getUser().getFirstName(),
                                                 userEvent.getUser().getEmail(),
                                                 (String) userEvent.getData().get("key"));
            default -> {}
        }
    }
}
