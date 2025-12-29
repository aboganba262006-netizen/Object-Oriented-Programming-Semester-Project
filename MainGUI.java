import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame {

    public MainGUI() {
        setTitle("Main Menu");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create buttons for Admin and Student
        JButton adminButton = new JButton("Admin");
        JButton studentButton = new JButton("Student");
        JButton backButton = new JButton("Back");

        // Add action listeners to buttons
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = JOptionPane.showInputDialog(null, "Enter Admin Password:");
                if (password != null && password.equals(Admin.getPassword())) {
                    new AdminGUI(); // Open Admin GUI
                    dispose(); // Close the current window
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect password. Access denied.");
                }
            }
        });

        studentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RoleSelectionGUI(); // Open Role Selection GUI for students
                dispose(); // Close the current window
            }
        });

        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            new RoleSelectionGUI(); // Navigate back to the Role Selection GUI
        });

        // Set layout and add buttons
        setLayout(new GridLayout(3, 1, 10, 10));
        add(adminButton);
        add(studentButton);
        add(backButton); // Add the back button to the layout

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI());
    }
}