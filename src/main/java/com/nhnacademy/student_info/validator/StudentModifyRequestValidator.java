package com.nhnacademy.student_info.validator;

import com.nhnacademy.student_info.domain.StudentModifyRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class StudentModifyRequestValidator implements Validator {

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    @Override
    public boolean supports(Class<?> clazz) {
        return StudentModifyRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        StudentModifyRequest request = (StudentModifyRequest) target;
        String name=request.getName();
        String email=request.getEmail();
        int score=request.getScore();
        String evaluation=request.getEvaluation();

        if(name.trim().isEmpty()){
            errors.rejectValue("name","이름은 1글자 이상을 사용해야합니다.");
        }

        Pattern pattern=Pattern.compile(EMAIL_PATTERN);
        if(!pattern.matcher(email).matches()){
            errors.rejectValue("email", "이메일 형식에 맞지 않습니다.");
        }

        if(score<0 || score>100){
            errors.rejectValue("score", "점수는 0점이상 100점이하로 작성해야합니다");
        }

        if(evaluation.trim().length()<=0 || evaluation.length()>200){
            errors.rejectValue("evaluation","문자열의 길이는 0보다 크고 200이하여야합니다.");
        }
    }
}