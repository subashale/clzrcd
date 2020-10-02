package com.clzrcd.client.student;

import com.clzrcd.helper.EmailHelper;
import com.clzrcd.helper.ImageExtensionFilter;
import com.clzrcd.models.faculty_courses.FacultyCoursesModel;
import com.clzrcd.models.student_model.StudentModel;
import com.clzrcd.services.FacultyCoursesService;
import com.clzrcd.services.StudentService;
import com.clzrcd.servicesimpl.FacultyCoursesImpl;
import com.clzrcd.servicesimpl.StudentServiceImpl;
import sun.tools.jar.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.text.DateFormatSymbols;
import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sun.javafx.iio.common.ImageTools.scaleImage;

public class StudentManagement {
    private JTextField firstNameTxt;
    private JComboBox dayCmb;
    private JComboBox monthCmb;
    private JComboBox yearCmb;
    private JTextField addressTxt;
    private JButton enrollButton;

    private JTable recordsTbl;
    private JButton deleteButton;
    private JButton updateButton;
    private JPanel rootPanel;
    private JComboBox enrollYearCmb;
    private JComboBox enrollSeasonCmb;
    private JTextField pvtEmailIdtxt;
    private JComboBox searchByCmb;
    private JTextField searchTxt;
    private JButton loadButton;
    private JTextField rollNoTxt;
    private JTextField countryTxt;
    private JCheckBox currentCheckBox;
    private JButton clearButton;
    private JTextField phoneNoTxt;
    private JComboBox facultyCmb;
    private JRadioButton maleRB;
    private JRadioButton femaleRB;
    private JRadioButton beautifulRB;
    private JPanel gender;
    private JButton uniEmailGenBtn;
    private JButton rollNoGenBtn;
    private JTextField uniEmailIdTxt;
    private JTextField middleNameTxt;
    private JTextField lastNametxt;
    private JLabel imageLbl;
    private JButton uploadImageBtn;
    private JPanel uploadImgPnl;
    private JButton imageRemoveBtn;
    private JButton reloadBtn;
    private Map<Integer, Integer> idLinker;


    StudentService studentService;
    FacultyCoursesService facultyCoursesService;

    public StudentManagement() {
        createUIComponents();
        populateRecords();

//
////        button actions
//        getSaveButton();
//        deleteRecord();
//        editRecord();
////        editButton.hide();
//        updateRecord();
//        clearButton();

        deleteButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filename = "C:\\Users\\subash\\Documents\\GitHub\\clzrcd\\viberimage.jpg";
                try {
                    BufferedImage image = ImageIO.read(new File(filename));
                    ImageIcon imageIcon = new ImageIcon(image);
                    imageLbl.setIcon(imageIcon);


                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        uploadImageBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.addChoosableFileFilter(new ImageExtensionFilter());
                fileChooser.setAcceptAllFileFilterUsed(false);

                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println(String.valueOf(100).length());
//                    System.out.println(selectedFile.getName().substring(selectedFile.getName().lastIndexOf('.') + 1));
                    try {
                        BufferedImage image = ImageIO.read(selectedFile);
                        ImageIcon imageIcon = new ImageIcon(image);

                        File sourceFile = new File(selectedFile.getAbsolutePath());
                        String imageLoc = "C:\\Users\\subash\\Desktop".concat("\\img\\");
                        if (!new File(String.valueOf(imageIcon)).exists()) {
                            new File(imageLoc).mkdir();
                        }

                        File destinationFile = new File(imageLoc+selectedFile.getName());

                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
                        FileOutputStream fileOutputStream = new FileOutputStream(
                                destinationFile);

                        int bufferSize;
                        byte[] bufffer = new byte[512];
                        while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                            fileOutputStream.write(bufffer, 0, bufferSize);
                        }
                        fileInputStream.close();
                        fileOutputStream.close();

                        Image img = imageIcon.getImage(); // transform it
                        Image newimg = img.getScaledInstance(158, 178,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

                        imageIcon = new ImageIcon(newimg);
                        imageLbl.setText("");
//                        uploadImgPnl.setName(selectedFile.getName().split(".")[0]);
                        uploadImgPnl.setToolTipText(selectedFile.getName());
                        imageLbl.setIcon(imageIcon);


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
            }
        });

        // remove uploaded image

        imageRemoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageLbl.setIcon(null);
                uploadImgPnl.setToolTipText("");
            }
        });

        // search combobox change selection event
        searchByCmb.setSelectedItem("All");
        if (searchByCmb.getSelectedItem().equals("All")) {
            searchTxt.setText("All data");
            searchTxt.setEditable(false);}

        searchByCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(searchByCmb.getSelectedItem());
                if (searchByCmb.getSelectedItem().equals("All")) {
                    searchTxt.setText("All data");
                    searchTxt.setEditable(false);
                } else {
                    searchTxt.setText("");
                    searchTxt.setEditable(true);
                }

            }
        });


    }

    public JPanel getRootPanel() { this.rootPanel.setSize(new Dimension(930, 500));return rootPanel; }


    private void populateRecords() {

        studentService = new StudentServiceImpl();
        List<StudentModel> studentList = studentService.getStudents();

        recordsTbl.setModel(new DefaultTableModel(
                null,
                new String[] {"Id", "F. Name", "M. Name", "L.Name", "DOB", "Roll No.", "Faculty", "Enrolled On", "Phone No.", "Address", "Country", "Uni. Email", "Pvt. Email"}
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        DefaultTableModel defaultTableModel = (DefaultTableModel) recordsTbl.getModel();

        idLinker = new HashMap<Integer, Integer>();

        int count = 0;
        for (StudentModel student:
             studentList) {
            count +=  1;
            idLinker.put(count, student.getId());
            defaultTableModel.addRow(new Object[] {count,
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getLastName(),
                    student.getDob(),
                    student.getRollNo(),
                    student.getFaculty(),
                    student.getEnrollYear()+student.getEnrollSeason(),
                    student.getGender(),
                    student.getAddress(),
                    student.getCountry(),
                    student.getUniEmailId(),
                    student.getPrivateEmailId()
            });
        }

//        set table attributes
        TableColumnModel columns = recordsTbl.getColumnModel();
        columns.getColumn(0).setMaxWidth(30);
        columns.getColumn(1).setMaxWidth(140);
        columns.getColumn(2).setMaxWidth(80);
        columns.getColumn(3).setMaxWidth(50);
        columns.getColumn(4).setMaxWidth(200);
        columns.getColumn(5).setMaxWidth(60);
        columns.getColumn(6).setMaxWidth(140);
        columns.getColumn(7).setMaxWidth(60);
        columns.getColumn(8).setMaxWidth(200);
        columns.getColumn(9).setMaxWidth(100);
//        columns.getColumn(10).setResizable(false);
//        columns.getColumn(10).setMaxWidth(0);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<10; i++) {
            columns.getColumn(i).setCellRenderer(cellRenderer);
        }




//        recordsTbl.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                if (e.getClickCount() == 2) {
//                    System.out.println("double clicked");
//
//                    int row = recordsTbl.getSelectedRow();
//                    int idTbl = idLinker.get((int) recordsTbl.getValueAt(row, 0));
//                    StudentModel studentById = studentService.getById(idTbl);
//
//                    // set to fields
//                    firstNameTxt.setText(studentById.getName());
//
//                    dayCmb.setSelectedItem(studentById.getDob().split("-")[0]);
//                    monthCmb.setSelectedItem(studentById.getDob().split("-")[1]);
//                    yearCmb.setSelectedItem(studentById.getDob().split("-")[2]);
//                    System.out.println(studentById.getDob().split("-")[0].concat(" :is DD"));
//
//                    rollnoTxt.setText(studentList.getRll);
//                    facultyTxt.setText(faculty);
//                    semesterTxt.setText(semester);
//                    collegeTxt.setText(collegeName);
//
//                    if (gender.equals("Male")) {
//                        maleRadioButton.setSelected(true);
//                    } else if (gender.equals("Female")) {
//                        femaleRadioButton.setSelected(true);
//                    } else {
//                        otherRadioButton.setSelected(true);
//                    }
//                    addressTxt.setText(address);
//                    phoneNoTxt.setText(phoneNo);
//
//
//                    Student student = new Student();
//                    student.setId(id);
//                    student.setName(name);
//                    student.setDob(dob);
//                    student.setRollNo(rollNo);
//                    student.setFaculty(faculty);
//                    student.setSemester(semester);
//                    student.setCollegeName(collegeName);
//                    student.setGender(gender);
//                    student.setAddress(address);
//                    student.setPhoneNo(phoneNo);
//
////                    firstNameTxt.setText(recordsTbl.getValueAt(row, 1).toString());
//                }
//            }
//        });

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

        //day combo desing

        dayCmb.setModel(new DefaultComboBoxModel(new String[] {"DD"}));
        for (int i=1; i<=32; i++) { dayCmb.addItem(String.valueOf(i));}

        //month combo desing
        monthCmb.setModel(new DefaultComboBoxModel(new String[] {"MMM"}));
        String[] shortMonths = new DateFormatSymbols().getShortMonths();
        for (int i=1; i < shortMonths.length; i++) {
            monthCmb.addItem(shortMonths[i-1]);
        }

        // year combo
        yearCmb.setModel(new DefaultComboBoxModel(new String[] {"YYYY"}));
        for (int i=1; i<32; i++) {
            yearCmb.addItem(String.valueOf(i+1989));
        }

        // faculty combo
        facultyCmb.setModel(new DefaultComboBoxModel());
        facultyCoursesService = new FacultyCoursesImpl();
        List<FacultyCoursesModel> facultyCoursesModels = facultyCoursesService.getFaculties();
        for (FacultyCoursesModel facultyData: facultyCoursesModels) {
            facultyCmb.addItem(facultyData.getField_value());
        }

        //year enrollment
        int currentYear = Year.now().getValue();
        enrollYearCmb.setModel(new DefaultComboBoxModel());
        for (int i=0; i<=3; i++) {
            enrollYearCmb.addItem(String.valueOf(i+currentYear));
        }


        //season enrollment
        enrollSeasonCmb.setModel(new DefaultComboBoxModel());
        enrollSeasonCmb.addItem("fall");
        enrollSeasonCmb.addItem("winter");

        // search by
        searchByCmb.setModel(new DefaultComboBoxModel());
        searchByCmb.addItem("All"); // reload, send make empty text box and get all student data
        searchByCmb.addItem("First Name");
        searchByCmb.addItem("Last Name");
        searchByCmb.addItem("Roll No");
        searchByCmb.addItem("Email Id");

        ////        radio button group
        ButtonGroup group = new ButtonGroup();
        group.add(maleRB);
        group.add(femaleRB);
        group.add(beautifulRB);

        // uni email generator
        uniEmailGenBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMinimumEntry()) {
                    String emailId = uniEmailIdTxt.getText();
                    EmailHelper emailHelper = new EmailHelper();
                    if (emailId.isEmpty()) {
                        String newUniEmail = emailHelper.generateUniEmailId(lastNametxt.getText().toLowerCase());
                        uniEmailIdTxt.setText(newUniEmail);
                    } else {
                        //if @ symbole then remove rest of the words
                        if(!emailHelper.checkEmailId(emailId.split("@")[0])) {
                            JOptionPane.showMessageDialog(null, emailId+": already exists");
                        } else {
                            JOptionPane.showMessageDialog(null, emailId+": can be used");
                        }
                    }
                }
            }
        });

        // uni roll_no generator
        rollNoTxt.setEditable(false);
        rollNoGenBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isMinimumEntry()) {
                    EmailHelper emailHelper = new EmailHelper();
                    int newRollNumber = emailHelper.generateRollNo();
                    rollNoTxt.setText(String.valueOf(newRollNumber));
                }
            }
        });

    }

    public boolean isMinimumEntry() {
        if (firstNameTxt.getText().isEmpty() || lastNametxt.getText().isEmpty()
                || dayCmb.getSelectedItem().equals("DD") || monthCmb.getSelectedItem().equals("MMM")
                || yearCmb.getSelectedItem().equals("YYYY")) {
            JOptionPane.showMessageDialog(null, "Names filed, Birth date and Roll No. cannot be empty");
            return false;
        }
        return true;
    }

/*
    private void getSaveButton() {
        enrollButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student student = new Student();
                student.setName(firstNameTxt.getText());
//              for gender
                if (maleRadioButton.isSelected()) { student.setGender("Male");
                } else if (femaleRadioButton.isSelected()) {student.setGender("Female");
                } else {student.setGender("Other");}


                student.setRollNo(rollnoTxt.getText());
                student.setFaculty(facultyTxt.getText());
                student.setSemester(semesterTxt.getText());
                student.setCollegeName(collegeTxt.getText());
//              for dob
                String day = dayCmb.getSelectedItem().toString();
                String month = monthCmb.getSelectedItem().toString();
                String year = yearCmb.getSelectedItem().toString();
                String dob = day.concat("-").concat(month).concat("-").concat(year);
                student.setDob(dob);

                if (student.getName().isEmpty() || day.equals("DD") || month.equals("MMM") || year.equals("YYYY")
                        || student.getRollNo().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name, Birth date and Roll No. cannot be empty");
                    return;
                }

                student.setAddress(addressTxt.getText());
                student.setPhoneNo(phoneNoTxt.getText());

                StudentService studentService = new StudentServiceImp();
                boolean result = studentService.addStudent(student);

                if (result == true) {
                    JOptionPane.showMessageDialog(null, firstNameTxt.getText()+" inserted Successfully");
                    populateRecords();

                    recordsTbl.requestFocus();
                    recordsTbl.changeSelection(recordsTbl.getRowCount()-1, 1, false, false);
                }
            }
        });

    }

    private void deleteRecord(){
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(recordsTbl.getSelectedRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Please select at least one row to delete");
                    return;
                }
                int[] rows = recordsTbl.getSelectedRows();

                for (int row:
                     rows) {
                    StudentService studentService = new StudentServiceImp();
                    int id = (int) recordsTbl.getValueAt(row, 0);
                    System.out.println(idLinker.get(id));
                    studentService.deleteStudent(idLinker.get(id));
                }

                populateRecords();
            }
        });
    }

    private void editRecord() {
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(recordsTbl.getSelectedRowCount() == 1 ) {
                    int row = recordsTbl.getSelectedRow();

                    int id = (int) recordsTbl.getValueAt(row, 0);
                    String name = (String) recordsTbl.getValueAt(row, 1);
                    String dob = (String) recordsTbl.getValueAt(row, 2);
                    String rollNo = (String) recordsTbl.getValueAt(row, 3);
                    String faculty = (String) recordsTbl.getValueAt(row, 4);
                    String semester = (String) recordsTbl.getValueAt(row, 5);
                    String collegeName = (String) recordsTbl.getValueAt(row, 6);
                    String gender = (String) recordsTbl.getValueAt(row, 7);
                    String address = (String) recordsTbl.getValueAt(row, 8);
                    String phoneNo = (String) recordsTbl.getValueAt(row, 9);

                    // set to fields
                    firstNameTxt.setText(name);

                    dayCmb.setSelectedItem(dob.split("-")[0]);
                    monthCmb.setSelectedItem(dob.split("-")[1]);
                    yearCmb.setSelectedItem(dob.split("-")[2]);
                    System.out.println(dob.split("-")[0].concat(" :is DD"));

                    rollnoTxt.setText(rollNo);
                    facultyTxt.setText(faculty);
                    semesterTxt.setText(semester);
                    collegeTxt.setText(collegeName);

                    if (gender.equals("Male")) {
                        maleRadioButton.setSelected(true);
                    } else if (gender.equals("Female")) {
                        femaleRadioButton.setSelected(true);
                    } else {
                        otherRadioButton.setSelected(true);
                    }
                    addressTxt.setText(address);
                    phoneNoTxt.setText(phoneNo);


                    Student student = new Student();
                    student.setId(id);
                    student.setName(name);
                    student.setDob(dob);
                    student.setRollNo(rollNo);
                    student.setFaculty(faculty);
                    student.setSemester(semester);
                    student.setCollegeName(collegeName);
                    student.setGender(gender);
                    student.setAddress(address);
                    student.setPhoneNo(phoneNo);

                } else {
                    JOptionPane.showMessageDialog(null, "Please select only one row to edit");
                    return;
                }

            }
        });
    }

    private void updateRecord() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//              get record id form table check to database id
                int row = recordsTbl.getSelectedRow();
                int id = (int) recordsTbl.getValueAt(row, 0);

                System.out.println(idLinker.get(id));

                Student student = new Student();
                student.setId(idLinker.get(id));
                student.setName(firstNameTxt.getText());

//              for gender
                if (maleRadioButton.isSelected()) { student.setGender("Male");
                } else if (femaleRadioButton.isSelected()) {student.setGender("Female");
                } else {student.setGender("Other");}

                student.setRollNo(rollnoTxt.getText());
                student.setFaculty(facultyTxt.getText());
                student.setSemester(semesterTxt.getText());
                student.setCollegeName(collegeTxt.getText());

//              for dob
                String day = dayCmb.getSelectedItem().toString();
                String month = monthCmb.getSelectedItem().toString();
                String year = yearCmb.getSelectedItem().toString();
                String dob = day.concat("-").concat(month).concat("-").concat(year);
                student.setDob(dob);

                student.setAddress(addressTxt.getText());
                student.setPhoneNo(phoneNoTxt.getText());

                if (student.getName().isEmpty() || day.equals("DD") || month.equals("MMM") || year.equals("YYYY")
                        || student.getRollNo().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name, Birth date and Roll No. cannot be empty");
                    return;
                }


                StudentService studentService = new StudentServiceImp();
                boolean result = studentService.updateStudent(student);

                if (result == true) {
                    JOptionPane.showMessageDialog(null, firstNameTxt.getText()+" updated Successfully");
                    populateRecords();
                    recordsTbl.requestFocus();
                    recordsTbl.changeSelection(row, 1, false, false);
                }


            }
        });
    }

    private void clearButton() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set to fields
                firstNameTxt.setText("");

                dayCmb.setSelectedItem("DD");
                monthCmb.setSelectedItem("MMM");
                yearCmb.setSelectedItem("YYYY");
                rollnoTxt.setText("");
                facultyTxt.setText("");
                semesterTxt.setText("");
                collegeTxt.setText("");

                maleRadioButton.setSelected(false);
                femaleRadioButton.setSelected(false);
                otherRadioButton.setSelected(false);

                addressTxt.setText("");
                phoneNoTxt.setText("");
            }
        });
    }
    */
}
