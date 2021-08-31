package com.pam.labs.pharma.collaborator.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
@lombok.Getter
@lombok.Setter
public class CollabDataSourceProperties {
    private String url;
    private String username;
    private String password;
}
