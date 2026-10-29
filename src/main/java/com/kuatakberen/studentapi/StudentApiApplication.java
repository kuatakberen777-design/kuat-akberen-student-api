package com.kuatakberen.studentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentApiApplication {

    public static void main(String[] args) {
        System.out.println("=== Kuat Akberen Student API Starting ===");
        SpringApplication.run(StudentApiApplication.class, args);
    }
}
