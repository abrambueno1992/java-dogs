package com.abrahambueno.javadogs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SeedDatabase {
    @Bean
    public CommandLineRunner InitDB(DogRepository dogRepository) {
        return args -> {
            log.info("Seeding " + dogRepository.save(new Dogs("Springer", 50, false)));
            log.info("Seeding " + dogRepository.save(new Dogs("Bulldog", 50, true)));
            log.info("Seeding " + dogRepository.save(new Dogs("Collie", 50, false)));
            log.info("Seeding " + dogRepository.save(new Dogs("Boston Terrie", 35, true)));
            log.info("Seeding " + dogRepository.save(new Dogs("Corgie", 35, true)));

        };
    }
}
