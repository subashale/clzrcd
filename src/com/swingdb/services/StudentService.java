package com.swingdb.services;

import com.swingdb.models.Student;

import java.util.List;

public interface StudentService {
    public boolean addStudent(Student student);

    public List<Student> getStudent();

    public boolean deleteStudent(int id);

    public boolean updateStudent(Student student);

    public Student getById(int id);
}
