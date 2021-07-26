package com.heitor.cursomc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class ProfileDevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strateg;

    private final Logger LOG = LoggerFactory.getLogger(DBService.class);

    @Bean
    public Boolean instantiateDataBase(){

        if(strateg.equals("create")){
            dbService.instantiateDataBaseTeste();
        }

        LOG.info("PROFILE DE DESENVOLVIMENTO");



        return true;
    }
}
