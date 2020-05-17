package com.trelloclone.domain.model.user;

import com.trelloclone.domain.common.security.PasswordEncryptor;
import com.trelloclone.domain.model.user.exception.EmailAddressExistsException;
import com.trelloclone.domain.model.user.exception.RegistrationException;
import com.trelloclone.domain.model.user.exception.UsernameExistsException;
import com.trelloclone.domain.model.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * User registration domain service
 */
@Component
public class RegistrationManagement {

    private UserRepository repository;
    private PasswordEncryptor passwordEncryptor;

    public RegistrationManagement(UserRepository repository, PasswordEncryptor passwordEncryptor) {
        this.repository = repository;
        this.passwordEncryptor = passwordEncryptor;
    }

    public User register(String username, String emailAddress, String password) throws RegistrationException {
        User existingUser = repository.findByUsername(username);
        if (existingUser != null) {
            throw new UsernameExistsException();
        }

        existingUser = repository.findByEmailAddress(emailAddress.toLowerCase());
        if (existingUser != null) {
            throw new EmailAddressExistsException();
        }

        String encryptedPassword = passwordEncryptor.encrypt(password);
        User newUser = User.create(username, emailAddress.toLowerCase(), encryptedPassword);
        repository.save(newUser);
        return newUser;
    }
}
