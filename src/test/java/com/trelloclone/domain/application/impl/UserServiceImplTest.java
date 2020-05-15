package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.commands.RegistrationCommand;
import com.trelloclone.domain.common.event.DomainEventPublisher;
import com.trelloclone.domain.common.mail.MailManager;
import com.trelloclone.domain.common.mail.MessageVariable;
import com.trelloclone.domain.model.user.RegistrationManagement;
import com.trelloclone.domain.model.user.User;
import com.trelloclone.domain.model.user.event.UserRegisteredEvent;
import com.trelloclone.domain.model.user.exception.EmailAddressExistsException;
import com.trelloclone.domain.model.user.exception.RegistrationException;
import com.trelloclone.domain.model.user.exception.UsernameExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private RegistrationManagement registrationManagementMock;
    private DomainEventPublisher domainEventPublisherMock;
    private MailManager mailManagerMock;
    private UserServiceImpl instance;

    @BeforeEach
    public void setUp() {
        registrationManagementMock = mock(RegistrationManagement.class);
        domainEventPublisherMock = mock(DomainEventPublisher.class);
        mailManagerMock = mock(MailManager.class);
        instance = new UserServiceImpl(registrationManagementMock, domainEventPublisherMock, mailManagerMock);
    }

    @Test
    public void register_nullCommand_shouldFail() {
        Throwable e = assertThrows(IllegalArgumentException.class, () -> {
            instance.register(null); // 예외가 발생해야 함
        });
        assertEquals("Parameter `command` must not be null", e.getMessage());
    }

    @Test
    public void register_existingUsername_shouldFail() throws RegistrationException {
        String username = "existing";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";
        doThrow(UsernameExistsException.class).when(registrationManagementMock)
                .register(username, emailAddress, password);

        RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);

        assertThrows(RegistrationException.class, () -> {
            instance.register(command); // 예외가 발생해야 함
        });
    }

    @Test
    public void register_existingEmailAddress_shouldFail() throws RegistrationException {
        String username = "sunny";
        String emailAddress = "existing@taskagile.com";
        String password = "MyPassword!";
        doThrow(EmailAddressExistsException.class).when(registrationManagementMock)
                .register(username, emailAddress, password);

        RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);
        assertThrows(RegistrationException.class, () -> {
            instance.register(command);
        });

    }

    @Test
    public void register_validCommand_shouldSucceed() throws RegistrationException {
        String username = "sunny";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";
        User newUser = User.create(username, emailAddress, password);
        when(registrationManagementMock.register(username, emailAddress, password))
                .thenReturn(newUser);
        RegistrationCommand command = new RegistrationCommand(username, emailAddress, password);

        instance.register(command);

        verify(mailManagerMock).send(
                emailAddress,
                "Welcome to TaskAgile",
                "welcome.ftl",
                MessageVariable.from("user", newUser)
        );
        verify(domainEventPublisherMock).publish(new UserRegisteredEvent(newUser));
    }
}