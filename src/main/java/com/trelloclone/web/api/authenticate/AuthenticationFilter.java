package com.trelloclone.web.api.authenticate;


import com.trelloclone.util.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public AuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/authentications", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {
        log.debug("Processing login request");

        String requestBody = IOUtils.toString(request.getReader());
        LoginRequest loginRequest = JsonUtils.toObject(requestBody, LoginRequest.class);
        if (loginRequest == null || loginRequest.isInvalid()) {
            throw new InsufficientAuthenticationException("Invalid authentication request");
        }

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password);
        return this.getAuthenticationManager().authenticate(token);
    }

    @Getter
    @Setter
    static class LoginRequest {
        private String username;
        private String password;

        public boolean isInvalid() {
            return StringUtils.isBlank(username) || StringUtils.isBlank(password);
        }
    }
}
