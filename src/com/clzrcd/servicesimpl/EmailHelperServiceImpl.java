package com.clzrcd.servicesimpl;

import com.clzrcd.db.DBConnection;
import com.clzrcd.models.email_helper.EmailHelperModel;
import com.clzrcd.services.EmailHelperService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmailHelperServiceImpl implements EmailHelperService {
    Connection conn;
    public EmailHelperServiceImpl() { conn = DBConnection.getDBConnection(); }

    @Override
    public List<EmailHelperModel> getEmails() {
        List<EmailHelperModel> emailIdList = new ArrayList<EmailHelperModel>();

        String selectAllQuery = "select uni_email_id from students_details";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            //id, name, dob, roll_no, faculty, enroll_date, gender,address, phone_no, country, email_id
            while(resultSet.next()) {
                EmailHelperModel emailId = new EmailHelperModel();
                emailId.setEmailId(resultSet.getString("uni_email_id"));
                emailIdList.add(emailId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emailIdList;
    }

    @Override
    public List<EmailHelperModel> getRollNos() {
        List<EmailHelperModel> rollNoList = new ArrayList<EmailHelperModel>();

        String selectAllQuery = "SELECT roll_no FROM students_details ORDER BY roll_no DESC LIMIT 1";

        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectAllQuery);
            //id, name, dob, roll_no, faculty, enroll_date, gender,address, phone_no, country, email_id
            while (resultSet.next()) {
                EmailHelperModel rollNO = new EmailHelperModel();
                rollNO.setRollNo(resultSet.getInt("roll_no"));
                rollNoList.add(rollNO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rollNoList;
    }

}
