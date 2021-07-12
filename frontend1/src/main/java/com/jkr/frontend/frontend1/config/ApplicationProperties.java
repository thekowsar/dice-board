package com.jkr.frontend.frontend1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
}
