package myemsproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyEmsProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyEmsProjectApplication.class, args);
    }

}
