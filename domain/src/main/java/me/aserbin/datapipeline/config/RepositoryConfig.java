package me.aserbin.datapipeline.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Provides configuration for JPARepository specific beans.
 */
@Configuration
@EnableJpaRepositories(basePackages = {"me.aserbin.datapipeline"})
@EntityScan(basePackages = {"me.aserbin.datapipeline.model"})
public class RepositoryConfig {
}
