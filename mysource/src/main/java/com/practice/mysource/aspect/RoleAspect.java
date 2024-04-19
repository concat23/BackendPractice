package com.practice.mysource.aspect;

import com.practice.mysource.domain.RequestContext;
import com.practice.mysource.entity.Role;
import com.practice.mysource.exception.ApiException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class RoleAspect {
    @Before("execution(* com.practice.mysource.repository.RoleRepository.save(..)) && args(role)")
    public void beforeRoleSave(Role role) {
        Long userId = RequestContext.getUserId();
        if (userId != null) {
            role.setCreatedBy(userId);
            role.setCreatedAt(LocalDateTime.now());
        } else {
            throw new ApiException("Cannot persist Role entity without a user ID in RequestContext.");
        }
    }
}
