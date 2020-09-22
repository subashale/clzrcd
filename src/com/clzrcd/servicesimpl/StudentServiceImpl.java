package com.clzrcd.servicesimpl;

import com.clzrcd.db.DBConnection;
import com.clzrcd.models.student_model.Student_Model;
import com.clzrcd.services.StudentService;

import java.sql.Connection;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    Connection conn;
    public StudentServiceImpl() { conn= DBConnection.getConnection(); }

    @Override
    public boolean addStudent(Student_Model student) {
        return false;
    }

    @Override
    public List<Student_Model> getStudents() {
        return null;
    }

    @Override
    public Student_Model getById(int id) {
        return null;
    }

    @Override
    public boolean deleteStudentById(int id) {
        return false;
    }

    @Override
    public boolean updateStudent(Student_Model student) {
        return false;
    }

    @Override
    public List<Student_Model> getStudentByEnrollDate(String enrollDate) {
        return null;
    }

    @Override
    public List<Student_Model> getDeletedStudents() {
        return null;
    }
}
