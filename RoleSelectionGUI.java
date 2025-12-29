import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RoleSelectionGUI extends JFrame {

    public RoleSelectionGUI() {
        setTitle("Student Authentication");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create buttons for Sign In, Sign Up, and Back
        JButton signInButton = new JButton("Sign In");
        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back");

        // Add action listeners to buttons
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });

        backButton.addActionListener(e -> {
            dispose(); // Close the current window
            new MainGUI(); // Navigate back to the Main GUI
        });

        // Set layout and add buttons
        setLayout(new GridLayout(3, 1, 10, 10));
        add(signInButton);
        add(signUpButton);
        add(backButton); // Add the back button to the layout

        setVisible(true);
    }

    private void handleSignIn() {
        String email = JOptionPane.showInputDialog(this, "Enter your email:");
        String password = JOptionPane.showInputDialog(this, "Enter your password:");

        boolean authenticated = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";");
                if (data.length == 4 && data[2].equals(email) && data[3].equals(password)) {
                    JOptionPane.showMessageDialog(this, "Welcome back, " + data[0] + " " + data[1] + "!");
                    authenticated = true;
                    new StudentGUI(new Student(data[0], data[1], data[2], data[3])); // Open Student GUI
                    dispose(); // Close the current window
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading student info: " + e.getMessage());
        }

        if (!authenticated) {
            JOptionPane.showMessageDialog(this, "Invalid email or password. Please try again.");
        }
    }

    private void handleSignUp() {
        String fname = JOptionPane.showInputDialog(this, "Enter your first name:");
        String lname = JOptionPane.showInputDialog(this, "Enter your last name:");
        String email = JOptionPane.showInputDialog(this, "Enter your email:");
        String password = JOptionPane.showInputDialog(this, "Enter your password:");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt", true))) {
            writer.write(fname + ";" + lname + ";" + email + ";" + password);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Sign-up successful! You can now sign in.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving student info: " + e.getMessage());
        }
    }
}
