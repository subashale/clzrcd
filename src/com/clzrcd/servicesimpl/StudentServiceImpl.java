package com.clzrcd.servicesimpl;

import com.clzrcd.db.DBConnection;
import com.clzrcd.models.student_model.StudentModel;
import com.clzrcd.services.StudentService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    Connection conn;
    public StudentServiceImpl() { conn= DBConnection.getDBConnection(); }

    @Override
    public boolean addStudent(StudentModel student) {
        return false;
    }

    @Override
    public List<StudentModel> getStudents() {
        List<StudentModel> studentList = new ArrayList<StudentModel>();

        String selectAllQuery = "select * from students_details where removed=1";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            //id, name, dob, roll_no, faculty, enroll_date, gender,address, phone_no, country, email_id
            while(resultSet.next()) {
                StudentModel student = new StudentModel();
                student.setId(resultSet.getInt("id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setMiddleName(resultSet.getString("middle_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setDob(resultSet.getString("dob"));
                student.setFaculty(resultSet.getString("faculty"));
                student.setEnrollSeason(resultSet.getString("enroll_season"));
                student.setEnrollYear(resultSet.getString("enroll_year"));
                student.setCurrentEnroll(resultSet.getString("current_enroll"));
                student.setAddress(resultSet.getString("address"));
                student.setPhoneNo(resultSet.getString("phone_no"));
                student.setGender(resultSet.getString("gender"));
                student.setPrivateEmailId(resultSet.getString("private_email_id"));
                student.setCountry(resultSet.getString("country"));
                student.setUniEmailId(resultSet.getString("uni_email_id"));
                student.setRollNo(resultSet.getString("roll_no"));
                student.setProfileImgLoc(resultSet.getString("profile_img_loc"));
                student.setModifyDate(resultSet.getString("modify_date"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

    @Override
    public StudentModel getById(int id) {
        return null;
    }

    @Override
    public boolean deleteStudentById(int id) {
        return false;
    }

    @Override
    public boolean updateStudent(StudentModel student) {
        return false;
    }

    @Override
    public List<StudentModel> getStudentByEnrollDate(String enrollDate) {
        return null;
    }

    @Override
    public List<StudentModel> getDeletedStudents() {
        return null;
    }
}
