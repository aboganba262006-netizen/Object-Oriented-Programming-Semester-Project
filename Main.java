import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Admin admin = new Admin("Admin", "admin@example.com", "1234");
            new AdminGUI(admin);
        });
    }
}
