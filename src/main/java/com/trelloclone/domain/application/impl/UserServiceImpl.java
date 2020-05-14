package com.trelloclone.domain.application.impl;

import com.trelloclone.domain.application.UserService;
import com.trelloclone.domain.application.commands.RegistrationCommand;
import com.trelloclone.domain.model.user.RegistrationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void register(RegistrationCommand command) throws RegistrationException {
        // TODO implement this
    }
}
