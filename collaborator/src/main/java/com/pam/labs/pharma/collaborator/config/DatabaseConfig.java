package com.pam.labs.pharma.collaborator.config;

import com.pam.labs.pharma.collaborator.util.CollabDataSourceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.inject.Inject;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.pam.labs.pharma.collaborator", entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class DatabaseConfig {

    private final CollabDataSourceProperties collabDataSourceProperties;

    @Bean(name = "dataSource")
    public DataSource collabDataSource() {
        return DataSourceBuilder.create()
                .url(collabDataSourceProperties.getUrl())
                .username(collabDataSourceProperties.getUsername())
                .password(collabDataSourceProperties.getPassword())
                .build();
    }

    @PersistenceContext
    @Bean(name = "entityManager")
    public LocalContainerEntityManagerFactoryBean customEntityManagerFactory(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder
                .dataSource(collabDataSource())
                .packages("com.pam.labs.pharma.collaborator")
                .build();
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(customEntityManagerFactory(entityManagerFactoryBuilder).getObject());
        jpaTransactionManager.setDataSource(collabDataSource());
        return jpaTransactionManager;
    }

}
