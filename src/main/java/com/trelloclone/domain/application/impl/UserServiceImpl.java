package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.UserService;
import com.trelloclone.domain.application.commands.RegistrationCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.common.mail.MailManager;
import com.trelloclone.domain.common.mail.MessageVariable;
import com.trelloclone.domain.model.user.RegistrationManagement;
import com.trelloclone.domain.model.user.SimpleUser;
import com.trelloclone.domain.model.user.User;
import com.trelloclone.domain.model.user.event.UserRegisteredEvent;
import com.trelloclone.domain.model.user.exception.RegistrationException;
import com.trelloclone.domain.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RegistrationManagement registrationManagement;
    private final DomainEventPublisher domainEventPublisher;
    private final MailManager mailManager;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("No user found");
        }
        User user;
        if (username.contains("@")) {
            user = userRepository.findByEmailAddress(username);
        } else {
            user = userRepository.findByUsername(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("No user found by `" + username + "`");
        }
        return new SimpleUser(user);
    }

    @Override
    public void register(RegistrationCommand command) throws RegistrationException {
        Assert.notNull(command, "Parameter `command` must not be null");

        User newUser = registrationManagement.register(
                command.getUsername(),
                command.getEmailAddress(),
                command.getPassword());

        sendWelcomeMessage(newUser);
        domainEventPublisher.publish(new UserRegisteredEvent(this, newUser));
    }

    private void sendWelcomeMessage(User user) {
        mailManager.send(
                user.getEmailAddress(),
                "Welcome to TrelloClone",
                "welcome.ftl",
                MessageVariable.from("user", user)
        );
    }
}
