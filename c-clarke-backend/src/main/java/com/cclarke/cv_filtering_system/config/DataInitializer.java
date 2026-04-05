package com.cclarke.cv_filtering_system.config;

import com.cclarke.cv_filtering_system.model.User;
import com.cclarke.cv_filtering_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("HRadminteam") == null) {
            User hrAdmin = new User();
            hrAdmin.setUsername("HRadminteam");
            hrAdmin.setPassword("admin123hr");
            hrAdmin.setRole("ADMIN");
            hrAdmin.setName("HR Administration Team");
            userRepository.save(hrAdmin);
        }
    }
}