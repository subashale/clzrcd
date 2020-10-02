package com.clzrcd.services;

import com.clzrcd.models.email_helper.EmailHelperModel;

import java.util.List;

public interface EmailHelperService {

    List<EmailHelperModel> getEmails();

    List<EmailHelperModel> getRollNos();
}
