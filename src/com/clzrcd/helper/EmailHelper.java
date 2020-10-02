package com.clzrcd.helper;

import com.clzrcd.models.email_helper.EmailHelperModel;
import com.clzrcd.services.EmailHelperService;
import com.clzrcd.servicesimpl.EmailHelperServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailHelper {

    public EmailHelper() {

    }
    public String generateUniEmailId(String last_name) {
        String domain = "@clzrcd.com";
        String userNameCon = "";
        String[] lastNameList = last_name.split(" ");
        int digit = 0;

        if (last_name.isEmpty()) {
            return "empty username";
        } else {

            for (String last:
                lastNameList) {
                    if (last.length() >= 3) {
                        userNameCon = userNameCon.concat(last.substring(0, 3));
                    } else {userNameCon = userNameCon.concat(last);}
                System.out.println(userNameCon);
            }

            EmailHelperService emailHelperService = new EmailHelperServiceImpl();
            List<EmailHelperModel> emailList = emailHelperService.getEmails(); // change to map to check value

            for (EmailHelperModel emailId: emailList) {

                digit++;
                if(!emailId.getEmailId().split("@")[0].equals(userNameCon.concat(String.valueOf(digit)))) {
                    // two decimal place
                    if (digit > 10) {
                        return userNameCon.concat("0"+String.valueOf(digit)).concat(domain);
                    }  else {
                        return userNameCon.concat(String.valueOf(digit)).concat(domain);
                    }

                } else { continue;}
            }
        }
        digit++;

        return userNameCon.concat(String.valueOf(digit)).concat(domain);
    }

    public boolean checkEmailId(String newUser) {
        EmailHelperService emailHelperService = new EmailHelperServiceImpl();
        List<EmailHelperModel> emailList = emailHelperService.getEmails(); // change to map to check value

        for (EmailHelperModel emailId: emailList) {
            if(emailId.getEmailId().split("@")[0].equals(newUser)) {
                return false;
            }
        }
        return true;
    }

    public int generateRollNo() {
        EmailHelperService emailHelperService = new EmailHelperServiceImpl();
        List<EmailHelperModel> rollNoList = emailHelperService.getRollNos();
        // year20+ winter = 0 summer 1+ 3 digit
        // eg 20+01+001 = 2010001 84324
        // for empty row
        int rollNo=0;
        if (rollNoList == null || rollNoList.isEmpty()) {
            rollNo=1001;
        } else {
            for (EmailHelperModel lastRollNo:
                 rollNoList) {
                rollNo = lastRollNo.getRollNo()+1;
            }
        }
        return rollNo;

    }
}
