package com.clzrcd.models.student_model;

public class StudentModel {

    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String dob;
    private String faculty;
    private String enrollSeason;
    private String enrollYear;
    private String currentEnroll;
    private String address;
    private String phoneNo;
    private String gender;
    private String privateEmailId;
    private String country;
    private String uniEmailId;
    private int rollNo;
    private String profileImgLoc;
    private String modifyDate;

    public int getId() {return id; }

    public void setId(int id) { this.id = id; }

    public String getFirstName() {return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName;}

    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDob() { return dob; }

    public void setDob(String dob) { this.dob = dob; }

    public int getRollNo() { return rollNo; }
    public void setRollNo(int rollNo) { this.rollNo = rollNo; }


    public String getFaculty() { return faculty; }

    public void setFaculty(String faculty) { this.faculty = faculty; }

    public String getEnrollSeason() { return enrollSeason; }

    public void setEnrollSeason(String enrollSeason) { this.enrollSeason = enrollSeason; }

    public String getGender() { return gender; }

    public void setGender(String gender) { this.gender = gender; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getPhoneNo() { return phoneNo; }

    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country;}

    public String getEnrollYear() { return enrollYear; }

    public void setEnrollYear(String enrollYear) { this.enrollYear = enrollYear; }

    public String getCurrentEnroll() { return currentEnroll; }

    public void setCurrentEnroll(String currentEnroll) { this.currentEnroll = currentEnroll; }

    public String getPrivateEmailId() { return privateEmailId; }

    public void setPrivateEmailId(String privateEmailId) { this.privateEmailId = privateEmailId; }

    public String getUniEmailId() { return uniEmailId; }

    public void setUniEmailId(String uniEmailId) { this.uniEmailId = uniEmailId; }

    public String getProfileImgLoc() { return profileImgLoc; }

    public void setProfileImgLoc(String profileImgLoc) { this.profileImgLoc = profileImgLoc; }

    public String getModifyDate() { return modifyDate; }

    public void setModifyDate(String modifyDate) { this.modifyDate = modifyDate; }

}
