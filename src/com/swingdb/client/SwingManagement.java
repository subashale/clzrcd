package com.swingdb.client;

import com.sun.org.apache.xpath.internal.objects.XNull;
import com.swingdb.models.Student;
import com.swingdb.services.StudentService;
import com.swingdb.servicesimpl.StudentServiceImp;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwingManagement {
    private JTextField nameTxt;
    private JComboBox dayCmb;
    private JComboBox monthCmb;
    private JComboBox yearCmb;
    private JTextField rollnoTxt;
    private JTextField facultyTxt;
    private JTextField semesterTxt;
    private JTextField collegeTxt;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JRadioButton otherRadioButton;
    private JTextField addressTxt;
    private JButton saveButton;

    private JTable recordsTbl;
    private JButton deleteButton;
    private JButton editButton;
    private JButton updateButton;
    private JPanel rootPanel;
    private JTextField phoneNoTxt;
    private JButton clearButton;
    private Map<Integer, Integer> idLinker;

    public SwingManagement() {
        populateRecords();
        createDayCombo();
        createMonthCombo();
        createYearCombo();

//        radio button group
        ButtonGroup group = new ButtonGroup();
        group.add(maleRadioButton);
        group.add(femaleRadioButton);
        group.add(otherRadioButton);

//        button actions
        getSaveButton();
        deleteRecord();
        editRecord();
//        editButton.hide();
        updateRecord();
        clearButton();

//        recordsTbl.addMouseListener(new MouseAdapter() {
//            public void mousePressed(MouseEvent mouseEvent) {
//                JTable table = recordsTbl.addMouseListener();
//                Point point = mouseEvent.getPoint();
//                int row = table.rowAtPoint(point);
//                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
//                    // your valueChanged overridden method
//                }
//            }
//        });

    }

    public JPanel getRootPanel() { return rootPanel; }

    private void populateRecords() {
//      2d array to fill table
//        Object[][] data = {
//                {"1", "Subash Ale Magar", "Informatics", "University of Passau"},
//                {"2", "Bipin", "Mathematics", "University of Passau"},
//                };

        StudentService studentService = new StudentServiceImp();
        List<Student> studentList = studentService.getStudent();

//        recordsTbl.setModel(new DefaultTableModel(
//                null,
//                new String[]{"Id", "Name", "DOB", "Roll No.", "Faculty", "Semester", "College Name", "Gender", "Address", "Phone No."}
//        ));
//

        recordsTbl.setModel(new DefaultTableModel(
                null,
                new String[]{"Id", "Name", "DOB", "Roll No.", "Faculty", "Semester", "College Name", "Gender", "Address", "Phone No."}
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });


        DefaultTableModel defaultTableModel = (DefaultTableModel) recordsTbl.getModel();

//        defaultTableModel.addColumn(new String[]{"Id", "Name", "Faculty", "College Name"});
//        Map<Integer, Integer> idLinker = new HashMap<Integer, Integer>();
        idLinker = new HashMap<Integer, Integer>();

        int count = 0;
        for (Student student:
             studentList) {
            count +=  1;
            idLinker.put(count, student.getId());
            defaultTableModel.addRow(new Object[] {count,
                    student.getName(),
                    student.getDob(),
                    student.getRollNo(),
                    student.getFaculty(),
                    student.getSemester(),
                    student.getCollegeName(),
                    student.getGender(),
                    student.getAddress(),
                    student.getPhoneNo()
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


        recordsTbl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() == 2) {
                    System.out.println("double clicked");

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

                    /* set to fields */
                    nameTxt.setText(name);

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

//                    nameTxt.setText(recordsTbl.getValueAt(row, 1).toString());
                }
            }
        });


    }
    private void createDayCombo() {
//        String[] dayArray = new String[33];
//        dayArray[0] = "DD";
//        for(int i = 1; i < 33; i++) {
//            if (i<10) {
//                dayArray[i] = "0".concat(String.valueOf(i));
//            } else {
//                dayArray[i] = String.valueOf(i);
//            }
//        }
        dayCmb.setModel(new DefaultComboBoxModel(new String[] {"DD"}));
        for (int i=1; i<=32; i++) {
            dayCmb.addItem(String.valueOf(i));
        }
    }

    private void createMonthCombo() {
//
//        String[] monthArray = new String[13];
//        monthArray[0] = "MM";
//        String[] shortMonths = new DateFormatSymbols().getShortMonths();
//
//        for (int i=1; i < shortMonths.length; i++) {
//            monthArray[i] = shortMonths[i-1];
//        }
        monthCmb.setModel(new DefaultComboBoxModel(new String[] {"MMM"}));
        String[] shortMonths = new DateFormatSymbols().getShortMonths();
        for (int i=1; i < shortMonths.length; i++) {
            monthCmb.addItem(shortMonths[i-1]);
        }

    }

    private void createYearCombo() {
//        String[] yearArray = new String[32];
//        yearArray[0] = "YYYY";
//        for (int i=1; i<32; i++) {
//            yearArray[i] = String.valueOf(i+1989);
//        }
        yearCmb.setModel(new DefaultComboBoxModel(new String[] {"YYYY"}));
        for (int i=1; i<32; i++) {
            yearCmb.addItem(String.valueOf(i+1989));
        }
    }

    private void getSaveButton() {
        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Student student = new Student();
                student.setName(nameTxt.getText());
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
                    JOptionPane.showMessageDialog(null, nameTxt.getText()+" inserted Successfully");
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

                    /* set to fields */
                    nameTxt.setText(name);

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
                student.setName(nameTxt.getText());

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
                    JOptionPane.showMessageDialog(null, nameTxt.getText()+" updated Successfully");
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
                /* set to fields */
                nameTxt.setText("");

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
}
