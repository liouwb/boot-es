package com.liouxb.boot.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author liouwb
 */
@SpringBootApplication
@EntityScan("com.liouxb.boot.es.domain")
public class BootEsApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootEsApplication.class, args);
    }

}
