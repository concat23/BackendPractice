package com.practice.mysource.event;

import com.practice.mysource.entity.User;
import com.practice.mysource.enumeration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private User user;
    private EventType type;
    private Map<?,?> data;
}
