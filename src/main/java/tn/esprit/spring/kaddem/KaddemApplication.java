package tn.esprit.spring.kaddem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KaddemApplication {  // Correction ici (KaddemApplication au lieu de Kaddempplication)

    public static void main(String[] args) {
        SpringApplication.run(KaddemApplication.class, args);
    }
}
