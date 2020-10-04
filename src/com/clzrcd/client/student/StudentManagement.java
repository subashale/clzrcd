package com.clzrcd.client.student;

import com.clzrcd.helper.EmailHelper;
import com.clzrcd.helper.ImageExtensionFilter;
import com.clzrcd.models.faculty_courses.FacultyCoursesModel;
import com.clzrcd.models.student_model.StudentModel;
import com.clzrcd.services.FacultyCoursesService;
import com.clzrcd.services.StudentService;
import com.clzrcd.servicesimpl.FacultyCoursesImpl;
import com.clzrcd.servicesimpl.StudentServiceImpl;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private JTextField pvtEmailIdTxt;
    private JComboBox searchByCmb;
    private JTextField searchTxt;
    private JButton loadBtn;
    private JTextField rollNoTxt;
    private JTextField countryTxt;
    private JCheckBox currentCheckBox;
    private JButton clearButton;
    private JTextField phoneNoTxt;
    private JComboBox facultyCmb;
    private JRadioButton maleRB;
    private JRadioButton femaleRB;
    private JRadioButton beautifulRB;
    private JPanel genderPnl;
    private JButton uniEmailGenBtn;
    private JButton rollNoGenBtn;
    private JTextField uniEmailIdTxt;
    private JTextField middleNameTxt;
    private JTextField lastNametxt;
    private JLabel imageLbl;
    private JButton uploadImageBtn;
    private JPanel uploadImgPnl;
    private JButton imageRemoveBtn;
    private JScrollPane recordPnl;
    private JButton reloadBtn;
    private Map<Integer, Integer> idLinker;
    private File destinationFile = null;
    File selectedProfileImage = null;
    private String selectedProfileImageLoc = "";
    private String searchText;
    private String searchBy="All";
    StudentService studentService;
    FacultyCoursesService facultyCoursesService;
    private String stateRemoveImage = "New";
    private boolean doubleClickActive = false;

    public StudentManagement() {
        createUIComponents();
        populateRecords();
        getProfileImage();
        loadButton();
        saveButton();
        deleteRecord();
        updateRecord();
        imageRemover();
        clearButton();
    }

    public JPanel getRootPanel() { this.rootPanel.setSize(new Dimension(930, 500));return rootPanel; }

    private void populateRecords() {
        stateRemoveImage = "New";
        studentService = new StudentServiceImpl();
        List<StudentModel> studentList = null;

        if (searchBy.equals("All")) {
            studentList = studentService.getStudents();
        } else {
            studentList = studentService.getStudentsBy(searchBy, searchText);
        }


        recordsTbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        recordsTbl.setModel(new DefaultTableModel(
                null,
                new String[] {"Id", "F. Name", "M. Name", "L.Name", "DOB", "Roll No.", "Faculty", "Enrolled On",
                        "Phone No.", "Address", "Country", "Uni. Email"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        DefaultTableModel defaultTableModel = (DefaultTableModel) recordsTbl.getModel();

        idLinker = new HashMap<Integer, Integer>();

        int count = studentList.size()+1;
        for (StudentModel student:
             studentList) {
            count -=  1;
            idLinker.put(count, student.getId());
            defaultTableModel.addRow(new Object[] {count,
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getLastName(),
                    student.getDob(),
                    student.getRollNo(),
                    student.getFaculty(),
                    student.getEnrollYear().concat(" "+student.getEnrollSeason()),
                    student.getPhoneNo(),
                    student.getAddress(),
                    student.getCountry(),
                    student.getUniEmailId(),
                    student.getPrivateEmailId()
            });
        }

//        set table attributes
        TableColumnModel columns = recordsTbl.getColumnModel();
        columns.getColumn(0).setPreferredWidth(25);
        columns.getColumn(1).setPreferredWidth(150);
        columns.getColumn(2).setPreferredWidth(100);
        columns.getColumn(3).setPreferredWidth(150);
        columns.getColumn(4).setPreferredWidth(80);
        columns.getColumn(5).setPreferredWidth(50);
        columns.getColumn(6).setPreferredWidth(200);
        columns.getColumn(7).setPreferredWidth(80);
        columns.getColumn(8).setPreferredWidth(80);
        columns.getColumn(9).setPreferredWidth(200);
        columns.getColumn(10).setPreferredWidth(80);
        columns.getColumn(11).setPreferredWidth(150);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<10; i++) {
            columns.getColumn(i).setCellRenderer(cellRenderer);
        }

        recordsTbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    stateRemoveImage = "Record";
                    int row = recordsTbl.getSelectedRow();
                    int idTbl = idLinker.get((int) recordsTbl.getValueAt(row, 0));

                    StudentService studentServicee = new StudentServiceImpl();
                    StudentModel studentById = studentServicee.getById(idTbl);

                    // set to fields
                    firstNameTxt.setText(studentById.getFirstName());
                    middleNameTxt.setText(studentById.getMiddleName());
                    lastNametxt.setText(studentById.getLastName());

                    dayCmb.setSelectedItem(studentById.getDob().split("-")[0]);
                    monthCmb.setSelectedItem(studentById.getDob().split("-")[1]);
                    yearCmb.setSelectedItem(studentById.getDob().split("-")[2]);
                    facultyCmb.setSelectedItem(studentById.getFaculty());
                    enrollSeasonCmb.setSelectedItem(studentById.getEnrollSeason());
                    enrollYearCmb.setSelectedItem(studentById.getEnrollYear());
                    if(studentById.getCurrentEnroll().equals("true")) {
                        currentCheckBox.setSelected(true);
                    } else {
                        currentCheckBox.setSelected(false);
                    }
                    addressTxt.setText(studentById.getAddress());
                    phoneNoTxt.setText(studentById.getPhoneNo());

                    String gender = studentById.getGender();
                    if (gender.equals("Male")) {
                        maleRB.setSelected(true);
                    } else if (gender.equals("Female")) {
                        femaleRB.setSelected(true);
                    } else {
                        beautifulRB.setSelected(true);
                    }

                    pvtEmailIdTxt.setText(studentById.getPrivateEmailId());
                    countryTxt.setText(studentById.getCountry());
                    uniEmailIdTxt.setText(studentById.getUniEmailId());
                    rollNoTxt.setText(String.valueOf(studentById.getRollNo()));

                    // for image
                    imageLbl.setIcon(null);
                    displayImage(studentById.getProfileImgLoc());

                    // double click state check to avoid duplicate entry
                    doubleClickActive = true;
//                    firstNameTxt.setText(recordsTbl.getValueAt(row, 1).toString());
                }
            }
        });

    }

    private void createUIComponents() {

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
        // TODO: later link id with value for db
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
        searchByCmb.addItem("Uni Email Id");

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
                        //if @ symbole then remove rest of the words, later append domain
                        if(!emailHelper.checkEmailId(emailId.split("@")[0])) {
                            JOptionPane.showMessageDialog(null, emailId+": already exists");
                        } else {
                            uniEmailIdTxt.setText(emailId.split("@")[0]+"@clzrcd.com");
                            JOptionPane.showMessageDialog(null, emailId+": can be used");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Names filed, Birth date and Roll No. cannot be empty");
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
                }  else {
                    JOptionPane.showMessageDialog(null, "Names filed, Birth date and Roll No. cannot be empty");
                }
            }
        });

        // if all disable edition search field and get all the data
        searchByCmb.setSelectedItem("All");
        if (searchByCmb.getSelectedItem().equals("All")) {
            searchTxt.setText("All data");
            searchTxt.setEditable(false);}

        searchByCmb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    private void getProfileImage() {
        // profle image uploader
        uploadImageBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                fileChooser.addChoosableFileFilter(new ImageExtensionFilter());
                fileChooser.setAcceptAllFileFilterUsed(false);

                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedProfileImage = fileChooser.getSelectedFile();

                    selectedProfileImageLoc = selectedProfileImage.getAbsolutePath();
                    try {
                        BufferedImage image = ImageIO.read(selectedProfileImage);
                        ImageIcon imageIcon = new ImageIcon(image);

//                        File sourceFile = new File(selectedProfileImage.getAbsolutePath());
//                        String imageLoc = "C:\\Users\\subash\\Desktop".concat("\\img\\");
//                        if (!new File(String.valueOf(imageIcon)).exists()) {
//                            new File(imageLoc).mkdir();
//                        }
//
//                        destinationFile = new File(imageLoc+selectedProfileImage.getName());
//                        FileInputStream fileInputStream = new FileInputStream(sourceFile);
//                        FileOutputStream fileOutputStream = new FileOutputStream(
//                                destinationFile);
//
//                        int bufferSize;
//                        byte[] bufffer = new byte[512];
//                        while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
//                            fileOutputStream.write(bufffer, 0, bufferSize);
//                        }
//                        fileInputStream.close();
//                        fileOutputStream.close();

                        Image img = imageIcon.getImage(); // transform it
                        Image newimg = img.getScaledInstance(158, 178,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way

                        imageIcon = new ImageIcon(newimg);
                        imageLbl.setText("");
//                        uploadImgPnl.setName(selectedFile.getName().split(".")[0]);
                        uploadImgPnl.setToolTipText(selectedProfileImage.getName());
                        imageLbl.setIcon(imageIcon);


                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            }
        });

    }

    public void displayImage(String imagePath) {
        // profle image uploader
        File imageLocation = new File(imagePath);
        if (!imageLocation.exists()) {
            imageLbl.setText("No image found");
            return;
        } else {
            try {
                BufferedImage image = ImageIO.read(imageLocation);
                if (image == null) {
                    return;
                }
                ImageIcon imageIcon = new ImageIcon(image);

                Image img = imageIcon.getImage(); // transform it
                Image newimg = img.getScaledInstance(158, 178, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                //save image after change otherwise it will create 0 byte image size

                imageIcon = new ImageIcon(newimg);
                imageLbl.setText("");
//                        uploadImgPnl.setName(selectedFile.getName().split(".")[0]);
                uploadImgPnl.setToolTipText(imageLocation.getName());
                imageLbl.setIcon(imageIcon);

                // if profile image is not altered while double clicked in row than its ok else getProfileImage
                // if removed ....
                selectedProfileImage = imageLocation;

            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
    }

    public boolean isMinimumEntry() {
        if (firstNameTxt.getText().isEmpty() || lastNametxt.getText().isEmpty()
                || dayCmb.getSelectedItem().equals("DD") || monthCmb.getSelectedItem().equals("MMM")
                || yearCmb.getSelectedItem().equals("YYYY")) {
            return false;
        }
        return true;
    }

    private void loadButton() {
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectionOption = searchByCmb.getSelectedItem().toString();

                if (selectionOption.equals("All")) {
                    searchBy = "All";
                } else if (selectionOption.equals("First Name")) {
                    searchText = searchTxt.getText();
                    searchBy = "first_name";
                } else if (selectionOption.equals("Last Name")) {
                    searchText = searchTxt.getText();
                    searchBy = "last_name";
                } else if (selectionOption.equals("Roll No")) {
                    searchText = searchTxt.getText();
                    searchBy = "roll_no";
                } else if (selectionOption.equals("Uni Email Id")) {
                    searchText = searchTxt.getText();
                    searchBy = "uni_email_id";
                }
                populateRecords();
            }
        });
    }

    private void saveButton() {
        enrollButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(doubleClickActive);
                if (!isMinimumEntry() || uniEmailIdTxt.getText().isEmpty() || rollNoTxt.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name, Birth date and Roll No. cannot be empty");
                    return;
                } else if (doubleClickActive) {
                    JOptionPane.showMessageDialog(null, "Deselect row, press Clear All");
                    return;
                }

                StudentModel student = new StudentModel();

                student.setFirstName(firstNameTxt.getText());
                student.setMiddleName(middleNameTxt.getText());
                student.setLastName(lastNametxt.getText());
                // for dob
                String day = dayCmb.getSelectedItem().toString();
                String month = monthCmb.getSelectedItem().toString();
                String year = yearCmb.getSelectedItem().toString();
                String dob = day.concat("-").concat(month).concat("-").concat(year);
                student.setDob(dob);

                student.setFaculty(facultyCmb.getSelectedItem().toString());
                student.setEnrollSeason(enrollSeasonCmb.getSelectedItem().toString());

                student.setEnrollYear(enrollYearCmb.getSelectedItem().toString());
                student.setCurrentEnroll(String.valueOf(currentCheckBox.isSelected()));
                student.setAddress(addressTxt.getText());
                student.setPhoneNo(phoneNoTxt.getText());

//              for genderPnl
                if (maleRB.isSelected()) { student.setGender("Male");
                } else if (femaleRB.isSelected()) {student.setGender("Female");
                } else {student.setGender("Other");}

                student.setPrivateEmailId(pvtEmailIdTxt.getText());
                student.setCountry(countryTxt.getText());
                student.setUniEmailId(uniEmailIdTxt.getText());

                student.setRollNo(Integer.parseInt(rollNoTxt.getText()));
                // profile image is optional hence can update later.
                if (selectedProfileImage != null){
                    if (selectedProfileImage.exists()) {

                        try {
//                            BufferedImage image = ImageIO.read(selectedProfileImage);
//                            ImageIcon imageIcon = new ImageIcon(image);

                            File sourceFile = new File(selectedProfileImage.getAbsolutePath());
                            String imageLoc = "C:\\Users\\subash\\Desktop".concat("\\img\\"+student.getUniEmailId().split("@")[0]+"\\");
//                            String imageLoc = System.getProperty("user.dir").concat("\\img\\"+student.getUniEmailId().split("@")[0]+"\\");

                            if (!new File(imageLoc).exists()) {
                                new File(imageLoc).mkdir();
                            }

                            destinationFile = new File(imageLoc+selectedProfileImage.getName());
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

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        student.setProfileImgLoc(destinationFile.getAbsolutePath());
                    }
                } else {
                    student.setProfileImgLoc("no image found");
                }

                // current modified date
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
//                System.out.println(dateFormat.format(date.toString()));
                student.setModifyDate(date.toString());


                StudentService studentService = new StudentServiceImpl();
                boolean result = studentService.addStudent(student);

                if (result == true) {
                    JOptionPane.showMessageDialog(null, firstNameTxt.getText()+" inserted Successfully");
                    populateRecords();

                    uniEmailIdTxt.setText("");
                    rollNoTxt.setText("");
                    recordsTbl.requestFocus();
                    recordsTbl.changeSelection(0, 1, false, false);

                    // change state of double click to avoid duplicate entry
                    doubleClickActive = false;

                } else {
                    if (destinationFile.exists()) {
                        destinationFile.delete();
                        imageLbl.setIcon(null);
                        uploadImgPnl.setToolTipText("");
                        doubleClickActive = false;
                    }
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

                int confirmBtn = JOptionPane.YES_NO_OPTION;
                int confirmDialog = JOptionPane.showConfirmDialog (null, "Your are deleting"+rows.length+"record/s",
                        "Deletion Warning",confirmBtn);
                if(confirmDialog == JOptionPane.YES_OPTION){
//                     Saving code here
                    for (int row:
                            rows) {
                        StudentService studentService = new StudentServiceImpl();
                        int id = (int) recordsTbl.getValueAt(row, 0);
                        studentService.deleteStudentById(idLinker.get(id));
                    }
                    populateRecords();
                }
            }
        });
    }

    private void updateRecord() {
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(recordsTbl.getSelectedRowCount() == 0 || !doubleClickActive) {
                    JOptionPane.showMessageDialog(null, "Please select one row to make changes" +
                            "\n double click to select record");
                    return;
                }

//              get record id form table check to database id
                int row = recordsTbl.getSelectedRow();
                int id = (int) recordsTbl.getValueAt(row, 0);

                if (id == 0 || !isMinimumEntry() || uniEmailIdTxt.getText().isEmpty() || rollNoTxt.getText().isEmpty() ) {
                    JOptionPane.showMessageDialog(null, "Name, Birth date and Roll No. cannot be empty");
                    return;
                }

                StudentModel student = new StudentModel();

                student.setId(idLinker.get(id));
                student.setFirstName(firstNameTxt.getText());
                student.setMiddleName(middleNameTxt.getText());
                student.setLastName(lastNametxt.getText());

                // for dob
                String day = dayCmb.getSelectedItem().toString();
                String month = monthCmb.getSelectedItem().toString();
                String year = yearCmb.getSelectedItem().toString();
                String dob = day.concat("-").concat(month).concat("-").concat(year);
                student.setDob(dob);

                student.setFaculty(facultyCmb.getSelectedItem().toString());
                student.setEnrollSeason(enrollSeasonCmb.getSelectedItem().toString());
                student.setEnrollYear(enrollYearCmb.getSelectedItem().toString());
                student.setCurrentEnroll(String.valueOf(currentCheckBox.isSelected()));
                student.setAddress(addressTxt.getText());
                student.setPhoneNo(phoneNoTxt.getText());

//              for genderPnl
                if (maleRB.isSelected()) { student.setGender("Male");
                } else if (femaleRB.isSelected()) {student.setGender("Female");
                } else {student.setGender("Other");}

                student.setPrivateEmailId(pvtEmailIdTxt.getText());
                student.setCountry(countryTxt.getText());
                student.setUniEmailId(uniEmailIdTxt.getText());
                student.setRollNo(Integer.parseInt(rollNoTxt.getText()));

                // profile image is optional hence can update later.
                if (selectedProfileImage != null){
                    if (selectedProfileImage.exists()) {
                        try {
                            File sourceFile = new File(selectedProfileImage.getAbsolutePath());
//                            String imageLoc = System.getProperty("user.dir").concat("\\img\\"+student.getUniEmailId().split("@")[0]+"\\");
                            String imageLoc = "C:\\Users\\subash\\Desktop".concat("\\img\\"+student.getUniEmailId().split("@")[0]+"\\");
                            if (!new File(imageLoc).exists()) {
                                new File(imageLoc).mkdir();
                            }

                            destinationFile = new File(imageLoc+selectedProfileImage.getName());
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

                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        student.setProfileImgLoc(destinationFile.getAbsolutePath());
                    }
                } else {
                    student.setProfileImgLoc("no image found");
                }

                // current modified date
                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                student.setModifyDate(date.toString());


                StudentService studentService = new StudentServiceImpl();
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

    private void imageRemover() {

        // profile image remover
        imageRemoveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageLbl.setIcon(null);
                uploadImgPnl.setToolTipText("");
                selectedProfileImage = null;
            }

        });
    }

    private void clearButton() {
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                populateRecords();
                firstNameTxt.requestFocus();
                // set to fields
                firstNameTxt.setText("");
                middleNameTxt.setText("");
                lastNametxt.setText("");
                dayCmb.setSelectedItem("DD");
                monthCmb.setSelectedItem("MMM");
                yearCmb.setSelectedItem("YYYY");
                facultyCmb.setSelectedItem("fall");
                enrollSeasonCmb.setSelectedItem("fall");
                enrollYearCmb.setSelectedItem("2020");
                currentCheckBox.setSelected(false);
                addressTxt.setText("");
                phoneNoTxt.setText("");
                maleRB.setSelected(false);
                femaleRB.setSelected(false);
                beautifulRB.setSelected(false);
                pvtEmailIdTxt.setText("");
                countryTxt.setText("");
                uniEmailIdTxt.setText("");
                rollNoTxt.setText("");
                imageLbl.setText("Upload image");
                imageLbl.setIcon(null);
            }
        });
    }


}
