package com.clzrcd.servicesimpl;

import com.clzrcd.db.DBConnection;
import com.clzrcd.models.faculty_courses.FacultyCoursesModel;
import com.clzrcd.services.FacultyCoursesService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FacultyCoursesImpl implements FacultyCoursesService {
    Connection conn;
    public FacultyCoursesImpl() {conn= DBConnection.getDBConnection();}
    @Override
    public List<FacultyCoursesModel> getFaculties() {
        List<FacultyCoursesModel> facultyList = new ArrayList<FacultyCoursesModel>();

        String selectAllQuery = "SELECT field_name, field_value FROM common ORDER BY id ASC";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            //id, name, dob, roll_no, faculty, enroll_date, gender,address, phone_no, country, email_id
            while(resultSet.next()) {
                FacultyCoursesModel faculty = new FacultyCoursesModel();
                faculty.setField_name(resultSet.getString("field_name"));
                faculty.setField_value(resultSet.getString("field_value"));
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return facultyList;
    }
}
