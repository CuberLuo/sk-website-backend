package net.sk.webbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebBackendApplication.class, args);
    }

}
