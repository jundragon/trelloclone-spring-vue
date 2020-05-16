package com.trelloclone.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Configuration
@Validated
@ConfigurationProperties("app")
public class ApplicationProperties {

    /**
     * 시스템에 의해 전송된 이메일의 기본 `from`
     */
    @Email
    @NotBlank
    private String mailFrom;

    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    public String getMailFrom() {
        return mailFrom;
    }
}
