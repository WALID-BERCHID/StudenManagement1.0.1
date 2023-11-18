import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteSpecific implements ActionListener {
    private JPanel panel;
    JButton deleteBtn = new JButton("Delete");

    JLabel enterId = new JLabel("Enter Student ID:");
    JTextField fieldId = new JTextField(20);

    public DeleteSpecific(JPanel panel) {
        this.panel = panel;

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(enterId, gbc);

        gbc.gridx = 1;
        panel.add(fieldId, gbc);

        gbc.gridx = 2;
        panel.add(deleteBtn, gbc);

        deleteBtn.addActionListener(this);
    }

    private void clearPanel() {
        // Remove all components from the panel
        panel.removeAll();

        // Revalidate and repaint the panel to reflect the changes
        panel.revalidate();
        panel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String studentIdStr = fieldId.getText().trim();

        if (studentIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a student ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int studentId = Integer.parseInt(studentIdStr);

            int choice = JOptionPane.showConfirmDialog(panel,
                    "Are you sure you want to delete the student with ID " + studentId + "?",
                    "Confirmation", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                // Clear the panel before performing the deletion
                clearPanel();

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

                try (Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/student", "root", "walid"
                )) {
                    try (PreparedStatement ps = con.prepareStatement("DELETE FROM student WHERE id = ?")) {
                        ps.setInt(1, studentId);

                        int n = ps.executeUpdate();

                        if (n > 0) {
                            JOptionPane.showMessageDialog(panel, "Student with ID " + studentId + " deleted successfully.");

                            // Show all students after deletion
                            new ShowStudents(this.panel);
                        } else {
                            JOptionPane.showMessageDialog(panel, "No student found with ID " + studentId, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(panel, "Invalid student ID. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
