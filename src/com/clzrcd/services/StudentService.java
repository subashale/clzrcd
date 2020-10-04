package com.clzrcd.services;

import com.clzrcd.models.student_model.StudentModel;

import java.util.List;

public interface StudentService {

    boolean addStudent(StudentModel student);
    List<StudentModel> getStudents();
    StudentModel getById(int id);
    boolean deleteStudentById(int id);
    boolean updateStudent(StudentModel student);
    List<StudentModel> getStudentByEnrollDate(String enrollDate);


//    deleted records
    List<StudentModel> getDeletedStudents();


    List<StudentModel> getStudentsBy(String searchBy, String searchText);
}
