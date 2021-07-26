package com.heitor.cursomc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class ProfileTestConfig {

    @Autowired
    private DBService dbService;

    private final Logger LOG = LoggerFactory.getLogger(DBService.class);

    @Bean
    public Boolean instantiateDataBase(){

        LOG.info("PROFILE DE TESTE");

        dbService.instantiateDataBaseTeste();

        return true;
    }
}
