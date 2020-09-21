package com.swingdb.servicesimpl;

import com.swingdb.db.DBConnection;
import com.swingdb.models.Student;
import com.swingdb.services.StudentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentServiceImp implements StudentService {

    Connection conn;
    public StudentServiceImp() {
        conn = DBConnection.getDBConnection();
    }
    @Override
    public boolean addStudent(Student student) {
        String insertQuery = "insert into student_details(" +
                "name, dob, roll_no, faculty, semester, college_name, gender, address, phone_no, remove)" +
                "value(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getDob());
            preparedStatement.setString(3, student.getRollNo());
            preparedStatement.setString(4, student.getFaculty());
            preparedStatement.setString(5, student.getSemester());
            preparedStatement.setString(6, student.getCollegeName());
            preparedStatement.setString(7, student.getGender());
            preparedStatement.setString(8, student.getAddress());
            preparedStatement.setString(9, student.getPhoneNo());
            preparedStatement.setInt(10, 1);

            preparedStatement.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    @Override
    public List<Student> getStudent() {
        List<Student> studentList = new ArrayList<Student>();

        String selectAllQuery = "select * from student_details where remove=1";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);

            while(resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("id"));
                student.setName(resultSet.getString("name"));
                student.setDob(resultSet.getString("dob"));
                student.setRollNo(resultSet.getString("roll_no"));
                student.setFaculty(resultSet.getString("faculty"));
                student.setSemester(resultSet.getString("semester"));
                student.setCollegeName(resultSet.getString("college_name"));
                student.setGender(resultSet.getString("gender"));
                student.setAddress(resultSet.getString("address"));
                student.setPhoneNo(resultSet.getString("phone_no"));

                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;

    }

    @Override
    public boolean deleteStudent(int id) {
//        String deleteQuery = "delete from student_details where id="+id;
        String updateQuery = "update student_details set remove=0 where id="+id;
        try {
            Statement statement = conn.createStatement();
            statement.execute(updateQuery);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateStudent(Student student) {
        String update = "UPDATE student_details SET "+
                "NAME='"+student.getName()+"', "+
                "dob='"+student.getDob()+"', "+
                "roll_no='"+student.getRollNo()+"', "+
                "faculty='"+student.getFaculty()+"', "+
                "semester='"+student.getSemester()+"', "+
                "college_name='"+student.getCollegeName()+"', "+
                "gender='"+student.getGender()+"', "+
                "address='"+student.getAddress()+"', "+
                "phone_no='"+student.getPhoneNo()+"' "+
                "WHERE id="+student.getId();

        try {
            Statement statement = conn.createStatement();
            statement.execute(update);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student getById(int id) {
        return null;
    }
}
