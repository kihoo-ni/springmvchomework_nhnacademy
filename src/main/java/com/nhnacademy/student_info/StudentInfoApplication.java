package com.nhnacademy.student_info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class StudentInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentInfoApplication.class, args);
    }

}
