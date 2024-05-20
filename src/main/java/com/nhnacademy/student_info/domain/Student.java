package com.nhnacademy.student_info.domain;

import lombok.Getter;
import lombok.Setter;

public class Student {
    @Getter
    private final String id;


    @Getter
    private final String password;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private int score;

    @Getter
    @Setter
    private String evaluation;

    public static Student create(String id, String password) {
        return new Student(id, password);
    }

    private Student(String id, String password) {
        this.id = id;
        this.password = password;
    }


}
