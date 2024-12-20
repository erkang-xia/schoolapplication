package com.example.schoolapplication.repository.impl;


import com.example.schoolapplication.entity.Student;
import com.example.schoolapplication.entity.Teacher;
import com.example.schoolapplication.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepositoryImpl implements StudentRepository {


    private final SessionFactory sessionFactory;

    public StudentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Student> fetchAll(Integer age) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Student";
            if (age != null) {
                hql += " where age = :age";
            }
            var query = session.createQuery(hql, Student.class);
            if (age != null) {
                query.setParameter("age", age);
            }
            return query.list();
        }
    }

    @Override
    public Student fetchById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, id);
        }
    }

    @Override
//    @Transactional
    public Student save(Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            return student;
        }
    }

    @Override
    public Student update(int id, Student student) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student existingStudent = session.get(Student.class, id);
            existingStudent.setName(student.getName());
            existingStudent.setAge(student.getAge());
            session.update(existingStudent);
            session.getTransaction().commit();
            return existingStudent;
        }
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Student student = session.get(Student.class, id);
            session.delete(student);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<Teacher> fetchTeachersByStudentId(int studentId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = """
            SELECT st.teacher
            FROM StudentTeacher st
            WHERE st.student.id = :studentId
        """;
            var query = session.createQuery(hql, Teacher.class);
            query.setParameter("studentId", studentId);
            return query.list();
        }
    }
}
