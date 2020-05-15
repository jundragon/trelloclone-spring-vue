package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.UserService;
import com.trelloclone.domain.application.commands.RegistrationCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.common.mail.MailManager;
import com.trelloclone.domain.common.mail.MessageVariable;
import com.trelloclone.domain.model.user.RegistrationManagement;
import com.trelloclone.domain.model.user.User;
import com.trelloclone.domain.model.user.event.UserRegisteredEvent;
import com.trelloclone.domain.model.user.exception.RegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final RegistrationManagement registrationManagement;
    private final DomainEventPublisher domainEventPublisher;
    private final MailManager mailManager;

    public UserServiceImpl(RegistrationManagement registrationManagement,
                           DomainEventPublisher domainEventPublisher,
                           MailManager mailManager) {
        this.registrationManagement = registrationManagement;
        this.domainEventPublisher = domainEventPublisher;
        this.mailManager = mailManager;
    }

    @Override
    public void register(RegistrationCommand command) throws RegistrationException {
        Assert.notNull(command, "Parameter `command` must not be null");

        User newUser = registrationManagement.register(
                command.getUsername(),
                command.getEmailAddress(),
                command.getPassword());

        sendWelcomeMessage(newUser);
        domainEventPublisher.publish(new UserRegisteredEvent(newUser));
    }

    private void sendWelcomeMessage(User user) {
        mailManager.send(
                user.getEmailAddress(),
                "Welcome to TaskAgile",
                "welcome.ftl",
                MessageVariable.from("user", user)
        );
    }
}
