import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddStudent implements ActionListener {
    GridBagConstraints gbc = new GridBagConstraints();
    Insets defaultInsets = new Insets(5, 5, 5, 5);

    JLabel labelNom = new JLabel("Nom:");
    JTextField fieldNom = new JTextField(20);

    JLabel labelPrenom = new JLabel("Prénom:");
    JTextField fieldPrenom = new JTextField(20);

    JLabel labelNote = new JLabel("Note:");
    JTextField fieldNote = new JTextField(20);
    JPanel panel;

    AddStudent(JPanel panel) {
        this.panel = panel;
        panel.setLayout(new GridBagLayout());

        JButton btnAjouter = new JButton("Add Student");

        gbc.gridy = 0;
        gbc.insets = defaultInsets;
        panel.add(labelNom, gbc);

        gbc.gridy = 1;
        panel.add(fieldNom, gbc);

        gbc.gridy = 2;
        panel.add(labelPrenom, gbc);

        gbc.gridy = 3;
        panel.add(fieldPrenom, gbc);

        gbc.gridy = 4;
        panel.add(labelNote, gbc);

        gbc.gridy = 5;
        panel.add(fieldNote, gbc);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(btnAjouter, gbc);

        btnAjouter.addActionListener(this);
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
        String nom = fieldNom.getText();
        String prenom = fieldPrenom.getText();
        float note = Float.parseFloat(fieldNote.getText());

        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Note: " + note);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student", "root", "walid"
        )) {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO student(nom, prenom, note) VALUES (?, ?, ?)")) {
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ps.setFloat(3, note);

                int n = ps.executeUpdate();

                if (n > 0) {
                    System.out.println("Row was added");

                    // Clear the panel before showing all students
                    clearPanel();

                    // Show all students
                    new ShowStudents(this.panel);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
