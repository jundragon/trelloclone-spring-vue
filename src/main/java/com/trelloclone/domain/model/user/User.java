package com.trelloclone.domain.model.user;

import com.trelloclone.domain.common.model.AbstractBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
public class User extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name = "email_address", nullable = false, length = 100, unique = true)
    private String emailAddress;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public UserId getId() {
        return new UserId(id);
    }

    /**
     * Create new user
     */
    public static User create(String username, String emailAddress, String firsName, String lastName, String password) {
        User user = new User();
        user.username = username;
        user.emailAddress = emailAddress;
        user.password = password;
        user.firstName = firsName;
        user.lastName = lastName;
        user.createdDate = LocalDateTime.now();
        return user;
    }

    public void updateName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getInitials() {
        return (firstName.substring(0, 1) + lastName.substring(0, 1)).toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) &&
                Objects.equals(emailAddress, user.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, emailAddress);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password=<Protected> " +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
