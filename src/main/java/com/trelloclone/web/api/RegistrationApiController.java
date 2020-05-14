package com.trelloclone.web.api;

import com.trelloclone.domain.application.UserService;
import com.trelloclone.domain.model.user.EmailAddressExistsException;
import com.trelloclone.domain.model.user.RegistrationException;
import com.trelloclone.domain.model.user.UsernameExistsException;
import com.trelloclone.web.payload.RegistrationPayload;
import com.trelloclone.web.result.ApiResult;
import com.trelloclone.web.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegistrationApiController {

    private final UserService service;

    @PostMapping("/api/registrations")
    public ResponseEntity<ApiResult> register(@Valid @RequestBody RegistrationPayload payload) {
        try {
            service.register(payload.toCommand());
            return Result.created();
        } catch (RegistrationException e) {
            String errorMessage = "Registration failed";
            if (e instanceof UsernameExistsException) {
                errorMessage = "Username already exists";
            } else if (e instanceof EmailAddressExistsException) {
                errorMessage = "Email address already exists";
            }
            return Result.failure(errorMessage);
        }
    }
}
