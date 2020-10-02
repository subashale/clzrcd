package com.clzrcd.services;

import com.clzrcd.models.faculty_courses.FacultyCoursesModel;

import java.util.List;

public interface FacultyCoursesService {
    // get list of faculties
    List<FacultyCoursesModel> getFaculties();
}
