package com.clzrcd.servicesimpl;

import com.clzrcd.db.DBConnection;
import com.clzrcd.models.student_model.StudentModel;
import com.clzrcd.services.StudentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    Connection conn;
    public StudentServiceImpl() { conn= DBConnection.getDBConnection(); }

    @Override
    public boolean addStudent(StudentModel student) {
        String insertQuery = "insert into students_details(" +
                "first_name, middle_name, last_name, dob, faculty, enroll_season, enroll_year, current_enroll," +
                "address, phone_no, gender, private_email_id, country, uni_email_id, roll_no, profile_img_loc," +
                "modify_date, removed)" +
                "value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getMiddleName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getDob());
            preparedStatement.setString(5, student.getFaculty());
            preparedStatement.setString(6, student.getEnrollSeason());
            preparedStatement.setString(7, student.getEnrollYear());
            preparedStatement.setString(8, student.getCurrentEnroll());

            preparedStatement.setString(9, student.getAddress());
            preparedStatement.setString(10, student.getPhoneNo());
            preparedStatement.setString(11, student.getGender());
            preparedStatement.setString(12, student.getPrivateEmailId());
            preparedStatement.setString(13, student.getCountry());
            preparedStatement.setString(14, student.getUniEmailId());
            preparedStatement.setInt(15, student.getRollNo());
            preparedStatement.setString(16, student.getProfileImgLoc());

            preparedStatement.setString(17, student.getModifyDate());
            preparedStatement.setInt(18, 1);

            preparedStatement.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<StudentModel> getStudents() {
        List<StudentModel> studentList = new ArrayList<StudentModel>();

        String selectAllQuery = "select * from students_details where removed=1 ORDER BY id DESC";
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
                student.setRollNo(resultSet.getInt("roll_no"));
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
//        List<StudentModel> studentList = new ArrayList<StudentModel>();
        StudentModel student = new StudentModel();
        String selectAllQuery = "SELECT * FROM students_details WHERE removed=1 AND id="+id;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            //id, name, dob, roll_no, faculty, enroll_date, gender,address, phone_no, country, email_id
            while(resultSet.next()) {
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
                student.setRollNo(resultSet.getInt("roll_no"));
                student.setProfileImgLoc(resultSet.getString("profile_img_loc"));
//                student.setModifyDate(resultSet.getString("modify_date"));
//                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return student;
    }

    @Override
    public boolean deleteStudentById(int id) {
        // update removed column value to 0
        String updateQuery = "UPDATE students_details SET removed=? where id=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public boolean updateStudent(StudentModel student) {
        String update = "UPDATE students_details SET " +
                "first_name=?, middle_name=?, last_name=?, dob=?, faculty=?, enroll_season=?, enroll_year=?, current_enroll=?, " +
                "address=?, phone_no=?, gender=?, private_email_id=?, country=?, uni_email_id=?, roll_no=?, profile_img_loc=?, " +
                "modify_date=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(update);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getMiddleName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getDob());
            preparedStatement.setString(5, student.getFaculty());
            preparedStatement.setString(6, student.getEnrollSeason());
            preparedStatement.setString(7, student.getEnrollYear());
            preparedStatement.setString(8, student.getCurrentEnroll());

            preparedStatement.setString(9, student.getAddress());
            preparedStatement.setString(10, student.getPhoneNo());
            preparedStatement.setString(11, student.getGender());
            preparedStatement.setString(12, student.getPrivateEmailId());
            preparedStatement.setString(13, student.getCountry());
            preparedStatement.setString(14, student.getUniEmailId());
            preparedStatement.setInt(15, student.getRollNo());
            preparedStatement.setString(16, student.getProfileImgLoc());
            preparedStatement.setString(17, student.getModifyDate());
            preparedStatement.setInt(18, student.getId());

            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    @Override
    public List<StudentModel> getStudentsBy(String searchBy, String searchText) {
        List<StudentModel> studentList = new ArrayList<StudentModel>();

        String selectAllQuery = "SELECT * FROM students_details where removed=1 AND " +
                searchBy+" LIKE '%"+searchText+"%' "+"ORDER BY id DESC";
        System.out.println(selectAllQuery);
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
                student.setRollNo(resultSet.getInt("roll_no"));
                student.setProfileImgLoc(resultSet.getString("profile_img_loc"));
                student.setModifyDate(resultSet.getString("modify_date"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }
}
