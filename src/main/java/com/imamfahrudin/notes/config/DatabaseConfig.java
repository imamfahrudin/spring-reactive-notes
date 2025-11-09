package com.imamfahrudin.notes.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

/**
 * Database configuration class for R2DBC reactive database access.
 * This configuration handles database initialization and schema setup.
 */
@Configuration
public class DatabaseConfig {

    /**
     * Creates and configures a ConnectionFactoryInitializer bean that initializes
     * the database schema on application startup.
     * 
     * <p>This method sets up a database populator that executes the schema.sql
     * script from the classpath to create the necessary database tables and
     * structure for the Notes application.</p>
     *
     * @param connectionFactory the R2DBC connection factory for reactive database access
     * @return a configured ConnectionFactoryInitializer that will run the schema initialization
     */
    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
            new ClassPathResource("schema.sql")
        );
        initializer.setDatabasePopulator(populator);
        
        return initializer;
    }
}
