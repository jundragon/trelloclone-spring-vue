package com.trelloclone.domain.model.user.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserRegisteredEventHandler {

    public void handleEvent(UserRegisteredEvent event) {
        log.debug("Handling `{}` registration event", event.getUser().getEmailAddress());
        // 도메인 이벤트 리스너를 위한 데모
    }
}
