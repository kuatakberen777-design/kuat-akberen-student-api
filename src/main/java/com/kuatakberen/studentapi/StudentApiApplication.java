package com.kuatakberen.studentapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentApiApplication {

    public static void main(String[] args) {
        System.out.println("=== Kuat Akberen Student API Starting ===");
        SpringApplication.run(StudentApiApplication.class, args);
    }

    @Bean
    CommandLineRunner seedIndividualTestData(StudentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Student("Kuat Akberen", "akberen.kuat@student.test", 20));
            }
        };
    }
}
