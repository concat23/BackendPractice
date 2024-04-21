package com.practice.mysource.aspect;

import com.practice.mysource.entity.User;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class UserAspect {

    @AfterReturning(pointcut = "execution(* com.practice.mysource.repository.UserRepository.save(..))", returning = "user")
    public void setDefaultValues(User user) {
        if (user.getCreatedAt() == null) {
            user.setCreatedAt(LocalDateTime.now());
        }
        if (user.getCreatedBy() == null) {
            user.setCreatedBy(0L);
        }
    }
}
