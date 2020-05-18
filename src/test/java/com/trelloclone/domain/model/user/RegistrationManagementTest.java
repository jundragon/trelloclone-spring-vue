package com.trelloclone.domain.model.user;

import com.trelloclone.domain.common.security.PasswordEncryptor;
import com.trelloclone.domain.model.user.exception.EmailAddressExistsException;
import com.trelloclone.domain.model.user.exception.RegistrationException;
import com.trelloclone.domain.model.user.exception.UsernameExistsException;
import com.trelloclone.domain.model.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class RegistrationManagementTest {

    private UserRepository repositoryMock;
    private PasswordEncryptor passwordEncryptorMock;
    private RegistrationManagement instance;

    @BeforeEach
    public void setUp() {
        repositoryMock = mock(UserRepository.class);
        passwordEncryptorMock = mock(PasswordEncryptor.class);
        instance = new RegistrationManagement(repositoryMock, passwordEncryptorMock);
    }

    @Test
    public void register_existedUsername_shouldFail() {
        String username = "existUsername";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";
        String firstName = "Existing";
        String lastName = "User";
        // We just return an empty user object to indicate an existing user
        when(repositoryMock.findByUsername(username)).thenReturn(new User());
        assertThrows(UsernameExistsException.class, () -> {
            instance.register(username, emailAddress, firstName, lastName, password);
        });
    }

    @Test
    public void register_existedEmailAddress_shouldFail() {
        String username = "sunny";
        String emailAddress = "exist@taskagile.com";
        String password = "MyPassword!";
        String firstName = "Sunny";
        String lastName = "Hu";
        // We just return an empty user object to indicate an existing user
        when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(new User());
        assertThrows(EmailAddressExistsException.class, () -> {
            instance.register(username, emailAddress, firstName, lastName, password);
        });
    }

    @Test
    public void register_uppercaseEmailAddress_shouldSucceedAndBecomeLowercase() throws RegistrationException {
        String username = "sunny";
        String emailAddress = "Sunny@TaskAgile.com";
        String password = "MyPassword!";
        String firstName = "Sunny";
        String lastName = "Hu";
        instance.register(username, emailAddress, firstName, lastName, password);
        User userToSave = User.create(username, emailAddress.toLowerCase(), firstName, lastName, password);
        verify(repositoryMock).save(userToSave);
    }

    @Test
    public void register_newUser_shouldSucceed() throws RegistrationException {
        String username = "sunny";
        String emailAddress = "sunny@taskagile.com";
        String password = "MyPassword!";
        String encryptedPassword = "EncryptedPassword";
        String firstName = "Sunny";
        String lastName = "Hu";
        User newUser = User.create(username, emailAddress, firstName, lastName, encryptedPassword);

        // repository mock 설정
        // 사용자가 존재하지 않음을 나타내고자 null 값 반환
        when(repositoryMock.findByUsername(username)).thenReturn(null);
        when(repositoryMock.findByEmailAddress(emailAddress)).thenReturn(null);
        doNothing().when(repositoryMock).save(newUser);
        // passwordEncryptor mock 설정
        when(passwordEncryptorMock.encrypt(password))
                .thenReturn("EncryptedPassword");

        User savedUser = instance.register(username, emailAddress, firstName, lastName, password);
        InOrder inOrder = inOrder(repositoryMock);
        inOrder.verify(repositoryMock).findByUsername(username);
        inOrder.verify(repositoryMock).findByEmailAddress(emailAddress);
        inOrder.verify(repositoryMock).save(newUser);
        verify(passwordEncryptorMock).encrypt(password);
        assertEquals(encryptedPassword, savedUser.getPassword(), "Saved user's password should be encrypted");
    }

}