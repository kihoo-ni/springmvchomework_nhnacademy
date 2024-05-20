package com.nhnacademy.student_info.repository;

import com.nhnacademy.student_info.domain.Student;
import com.nhnacademy.student_info.exception.StudentAlreadyExistsException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<String, Student> studentMap = new HashMap<>();

    @Override
    public boolean exists(String id) {
        return studentMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getStudent(id))
                .map( student -> student.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public List<Student> getStudents() {
        return studentMap.values().stream().toList();
    }

    @Override
    public Student getStudent(String id) {
        return studentMap.get(id);
    }

    @Override
    public void addStudent(Student student) {
        studentMap.put(student.getId(), student);
    }

    @Override
    public Student addStudent(String id, String password, String name, String email, int score, String evaluation) {
        if (exists(id)) {
            throw new StudentAlreadyExistsException();
        }

        Student student = Student.create(id, password);
        student.setName(name);
        student.setEmail(email);
        student.setScore(score);
        student.setEvaluation(evaluation);

        studentMap.put(id, student);

        return student;
    }

    @Override
    public Student modifyStudent(String id, String password, String name, String email, int score, String evaluation) {
        if (exists(id)) {
            throw new StudentAlreadyExistsException();
        }

        Student student = Student.create(id, password);
        student.setName(name);
        student.setEmail(email);
        student.setScore(score);
        student.setEvaluation(evaluation);

        studentMap.put(id, student);

        return student;
    }


}
