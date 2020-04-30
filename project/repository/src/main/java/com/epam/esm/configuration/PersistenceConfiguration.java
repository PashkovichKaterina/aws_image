package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.ResourceBundle;

@Configuration
@EnableTransactionManagement
public class PersistenceConfiguration {
    private static final String ENTITY_PACKAGE = "com.epam.esm";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String DATASOURCE_PROPERTY_FILE_PATH = "/datasource.properties";
    private static final String HIBERNATE_PROPERTY_FILE = "hibernate";

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan(ENTITY_PACKAGE);
        emf.setDataSource(createDataSource());
        emf.setJpaVendorAdapter(createJpaVendorAdapter());
        emf.setJpaProperties(createHibernateProperties());
        emf.afterPropertiesSet();
        return emf;
    }

    @Bean
    public DataSource createDataSource() {
        HikariConfig config = new HikariConfig(DATASOURCE_PROPERTY_FILE_PATH);
        return new HikariDataSource(config);
    }

    private JpaVendorAdapter createJpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties createHibernateProperties() {
        ResourceBundle bundle = ResourceBundle.getBundle(HIBERNATE_PROPERTY_FILE);
        Properties properties = new Properties();
        properties.setProperty(HIBERNATE_HBM2DDL_AUTO, bundle.getString(HIBERNATE_HBM2DDL_AUTO));
        properties.setProperty(HIBERNATE_DIALECT, bundle.getString(HIBERNATE_DIALECT));
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}