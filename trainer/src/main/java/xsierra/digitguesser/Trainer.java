package xsierra.digitguesser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Trainer {

    public static void main(String[] args) {
        SpringApplication.run(Trainer.class, args);
    }

}
