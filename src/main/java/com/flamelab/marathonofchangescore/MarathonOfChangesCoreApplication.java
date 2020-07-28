package com.flamelab.marathonofchangescore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MarathonOfChangesCoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarathonOfChangesCoreApplication.class, args);
    }

}
