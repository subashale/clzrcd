package com.clzrcd.services;

import com.clzrcd.models.student_model.Student_Model;

import java.util.List;

public interface StudentService {

    boolean addStudent(Student_Model student);
    List<Student_Model> getStudents();
    Student_Model getById(int id);
    boolean deleteStudentById(int id);
    boolean updateStudent(Student_Model student);
    List<Student_Model> getStudentByEnrollDate(String enrollDate);

//    deleted records
    List<Student_Model> getDeletedStudents();


}
