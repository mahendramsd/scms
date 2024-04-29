package com.minden.ai.scm.config;

import com.minden.ai.scm.domain.User;
import com.minden.ai.scm.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mahendrasridayarathna
 * @created 29/04/2024 - 1:31 pm
 * @project IntelliJ IDEA
 */
@Configuration
public class StartupConfig {
    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            if(userRepository.findByUsername("user").isPresent()) {
                return;
            }
            User user = new User();
            user.setUsername("user");
            user.setPassword("$2a$10$elkhgBcGrww/uxmgQqsti.YDmqqgFji4cmqmE8FjDG5xv8trG84gq"); // Make sure to encode this password if you're using password encoder
            userRepository.save(user);
        };
    }
}
