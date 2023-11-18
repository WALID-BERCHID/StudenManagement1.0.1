import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteStudents implements ActionListener {
    private JPanel panel;

    public DeleteStudents(JPanel panel) {
        this.panel = panel;

        JButton deleteBtn = new JButton("Delete All");
        deleteBtn.addActionListener(this);

        // Add styling to the button
        deleteBtn.setBackground(new Color(255, 87, 34)); // Orange background color
        deleteBtn.setForeground(Color.WHITE); // White text color
        deleteBtn.setFont(new Font("Arial", Font.BOLD, 14)); // Bold font size 14

        panel.add(deleteBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(panel,
                "Are you sure you want to delete all students?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student", "root", "walid"
            )) {
                try (PreparedStatement ps = con.prepareStatement("DELETE FROM student")) {

                    int n = ps.executeUpdate();

                    if (n > 0) {
                        System.out.println("Deleting all students...");
                    }
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Delete Students Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            frame.add(mainPanel);

            // Create an instance of DeleteStudents and add it to the main panel
            DeleteStudents deleteStudents = new DeleteStudents(mainPanel);

            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
