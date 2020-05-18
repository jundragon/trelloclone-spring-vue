package com.trelloclone.infra.repository;

import com.trelloclone.domain.model.user.User;
import com.trelloclone.domain.model.user.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
class HibernateUserRepositoryTest {

    @TestConfiguration
    public static class UserRepositoryTestContextConfiguration {
        @Bean
        public UserRepository userRepository(EntityManager entityManager) {
            return new HibernateUserRepository(entityManager);
        }
    }

    @Autowired UserRepository repository;

    @Test
    public void save_nullUsernameUser_shouldFail() {
        User inavlidUser = User.create(null, "sunny@taskagile.com", "Sunny", "Hu", "MyPassword!");
        assertThrows(PersistenceException.class, () -> {
            repository.save(inavlidUser);
        });
    }

    @Test
    public void save_nullEmailAddressUser_shouldFail() {
        User inavlidUser = User.create("sunny", null, "Sunny", "Hu", "MyPassword!");
        assertThrows(PersistenceException.class, () -> {
            repository.save(inavlidUser);
        });
    }

    @Test
    public void save_nullPasswordUser_shouldFail() {
        User inavlidUser = User.create("sunny", "sunny@taskagile.com", "Sunny", "Hu", null);
        assertThrows(PersistenceException.class, ()->{
            repository.save(inavlidUser);
        });
    }

    @Test
    public void save_validUser_shouldSuccess() {
        String username = "sunny";
        String emailAddress = "sunny@taskagile.com";
        String firstName = "Sunny";
        String lastName = "Hu";
        User newUser = User.create(username, emailAddress, firstName, lastName, "MyPassword!");
        repository.save(newUser);
        assertNotNull(newUser.getId(), "New user's id should be generated");
        assertNotNull(newUser.getCreatedDate(), "New user's created date should be generated");
        assertEquals(username, newUser.getUsername());
        assertEquals(emailAddress, newUser.getEmailAddress());
        assertEquals(firstName, newUser.getFirstName());
        assertEquals(lastName, newUser.getLastName());
    }

    @Test
    public void save_usernameAlreadyExist_shouldFail() {
        // Create already exist user
        String username = "sunny";
        String emailAddress = "sunny@taskagile.com";
        User alreadyExist = User.create(username, emailAddress, "Sunny", "Hu", "MyPassword!");
        repository.save(alreadyExist);

        try {
            User newUser = User.create(username, "new@taskagile.com", "Sunny", "Hu", "MyPassword!");
            repository.save(newUser);
        } catch (Exception e) {
            assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
        }
    }

    @Test
    public void save_emailAddressAlreadyExist_shouldFail() {
        // Create already exist user
        String username = "sunny";
        String emailAddress = "sunny@taskagile.com";
        User alreadyExist = User.create(username, emailAddress, "Sunny", "Hu", "MyPassword!");
        repository.save(alreadyExist);

        try {
            User newUser = User.create("new", emailAddress, "Sunny", "Hu", "MyPassword!");
            repository.save(newUser);
        } catch (Exception e) {
            assertEquals(ConstraintViolationException.class.toString(), e.getCause().getClass().toString());
        }
    }

    @Test
    public void findByEmailAddress_notExist_shouldReturnEmptyResult() {
        String emailAddress = "sunny@taskagile.com";
        User user = repository.findByEmailAddress(emailAddress);
        assertNull(user, "No user should by found");
    }

    @Test
    public void findByEmailAddress_exist_shouldReturnResult() {
        String emailAddress = "sunny@taskagile.com";
        String username = "sunny";
        User newUser = User.create(username, emailAddress, "Sunny", "Hu", "MyPassword!");
        repository.save(newUser);
        User found = repository.findByEmailAddress(emailAddress);
        assertEquals(username, found.getUsername(), "Username should match");
    }

    @Test
    public void findByUsername_notExist_shouldReturnEmptyResult() {
        String username = "sunny";
        User user = repository.findByUsername(username);
        assertNull(user, "No user should by found");
    }

    @Test
    public void findByUsername_exist_shouldReturnResult() {
        String username = "sunny";
        String emailAddress = "sunny@taskagile.com";
        User newUser = User.create(username, emailAddress, "Sunny", "Hu", "MyPassword!");
        repository.save(newUser);
        User found = repository.findByUsername(username);
        assertEquals(emailAddress, found.getEmailAddress(), "Email address should match");
    }
}