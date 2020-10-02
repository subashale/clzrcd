import com.clzrcd.client.student.StudentManagement;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello worlds");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGui();
            }
        });
    }

    private static void createGui() {
        StudentManagement ui = new StudentManagement();
        JPanel root = ui.getRootPanel();
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(928, 500));
        frame.setMinimumSize(new Dimension(928, 500));
//        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
