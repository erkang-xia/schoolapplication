package com.example.schoolapplication.repository;

import com.example.schoolapplication.entity.Student;
import com.example.schoolapplication.entity.Teacher;

import java.util.List;

public interface StudentRepository {
    List<Student> fetchAll(Integer age);
    Student fetchById(int id);
    Student save(Student student);
    Student update(int id, Student student);
    void delete(int id);
    List<Teacher> fetchTeachersByStudentId(int studentId);
}
