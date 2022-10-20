package com.js.mealkitecommerce.app.global.initData;

import com.js.mealkitecommerce.app.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevInitData implements InitDataBefore {
    @Bean
    CommandLineRunner initData(CustomerService customerService) {
        return args -> {
            before(customerService);
        };
    }
}