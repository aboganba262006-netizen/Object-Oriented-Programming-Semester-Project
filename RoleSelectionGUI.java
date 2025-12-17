import javax.swing.*;
import java.awt.*;

public class RoleSelectionGUI extends JFrame {

public RoleSelectionGUI() {
setTitle("Select Role");
setSize(300, 200);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLocationRelativeTo(null);

JButton adminBtn = new JButton("Admin");
JButton studentBtn = new JButton("Student");

adminBtn.addActionListener(e -> {
new AdminGUI(new Admin("Admin", "admin@mail.com", "1234"));
dispose();
});

studentBtn.addActionListener(e -> {
new StudentGUI(new Student("Student", "student@mail.com", "1234"));
dispose();
});

setLayout(new GridLayout(2,1,10,10));
add(adminBtn);
add(studentBtn);

setVisible(true);
}
}
