import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateStudent implements ActionListener {
    GridBagConstraints gbc = new GridBagConstraints();
    Insets defaultInsets = new Insets(5, 5, 5, 5);

    JLabel annonce = new JLabel("Enter the id of the Student you want to update then add the new parameters ..");
    JLabel EnterId = new JLabel("Enter L'ID :");
    JTextField fieldId = new JTextField(20);

    JLabel EnterNom = new JLabel("Enter le Nouveau Nom :");
    JTextField fieldNom = new JTextField(20);

    JLabel EnterPrenom = new JLabel("Enter le Nouveau Prenom :");
    JTextField fieldPrenom = new JTextField(20);

    JLabel EnterNote = new JLabel("Enter la Nouvelle Note :");
    JTextField fieldNote = new JTextField(20);

    JButton btnUpdate = new JButton("Update Student");

    JPanel panel;

    public UpdateStudent(JPanel panel) {
        this.panel = panel;

        panel.setLayout(new GridBagLayout());

        gbc.gridy = 0;
        gbc.insets = defaultInsets;
        gbc.gridwidth = 2;
        panel.add(annonce, gbc);

        gbc.gridy = 1;
        panel.add(EnterId, gbc);

        gbc.gridy = 2;
        panel.add(fieldId, gbc);

        gbc.gridy = 3;
        panel.add(EnterNom, gbc);

        gbc.gridy = 4;
        panel.add(fieldNom, gbc);

        gbc.gridy = 5;
        panel.add(EnterPrenom, gbc);

        gbc.gridy = 6;
        panel.add(fieldPrenom, gbc);

        gbc.gridy = 7;
        panel.add(EnterNote, gbc);

        gbc.gridy = 8;
        panel.add(fieldNote, gbc);

        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(btnUpdate, gbc);

        btnUpdate.addActionListener(this);
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

        int id = Integer.parseInt(fieldId.getText());
        String nom = fieldNom.getText();
        String prenom = fieldPrenom.getText();
        float note = Float.parseFloat(fieldNote.getText());

        System.out.println("id " + id);
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
            try (PreparedStatement ps = con.prepareStatement("UPDATE student set nom = ?, prenom = ?, note = ? where id = ?")) {
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ps.setFloat(3, note);
                ps.setInt(4, id);

                int n = ps.executeUpdate();

                if (n > 0) {
                    System.out.println("Row was updated");

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
