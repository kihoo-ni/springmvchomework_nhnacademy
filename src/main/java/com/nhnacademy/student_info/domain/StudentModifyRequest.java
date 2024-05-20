package com.nhnacademy.student_info.domain;

import lombok.Value;

@Value
public class StudentModifyRequest {
    String id;
    String password;

    String name;
    String email;
    int score;

    String evaluation;

}
