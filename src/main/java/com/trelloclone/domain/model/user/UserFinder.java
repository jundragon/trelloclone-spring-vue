package com.trelloclone.domain.model.user;

import com.trelloclone.domain.model.user.exception.UserNotFoundException;
import com.trelloclone.domain.model.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder {

    private final UserRepository userRepository;

    public User find(String usernameOrEmailAddress) throws UserNotFoundException {
        User user;
        if (usernameOrEmailAddress.contains("@")) {
            user = userRepository.findByEmailAddress(usernameOrEmailAddress);
        } else {
            user = userRepository.findByUsername(usernameOrEmailAddress);
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
