package com.cuscatlan.orders.infrastructure.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for ModelMapper.
 * <p>
 * This class provides a bean for ModelMapper, which is used to map 
 * between different object models in the application.
 * </p>
 */
@Configuration
public class MapperConfig {

    /**
     * Provides a singleton instance of ModelMapper.
     *
     * @return a new instance of ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
