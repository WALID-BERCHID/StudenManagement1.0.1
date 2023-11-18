import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowSpecific implements ActionListener {
    private JPanel panel;
    private JTextField idTextField;
    private JButton showBtn;
    private JTable table;

    public ShowSpecific(JPanel panel) {
        this.panel = panel;

        idTextField = new JTextField(10);
        showBtn = new JButton("Show Details");

        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Enter Student ID: "));
        panel.add(idTextField);
        panel.add(showBtn);

        showBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String enteredId = idTextField.getText().trim();

        if (enteredId.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "Please enter a student ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        try (Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/student", "root", "walid"
        )) {
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM student WHERE id = ?")) {
                ps.setString(1, enteredId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        // Create a DefaultTableModel with specified columns
                        DefaultTableModel tableModel = new DefaultTableModel();
                        tableModel.addColumn("Student ID");
                        tableModel.addColumn("Student Name");
                        tableModel.addColumn("Student Prenom");

                        // Add data to the DefaultTableModel
                        String studentIdStr = rs.getString("id");
                        String studentNom = rs.getString("nom");
                        String studentPrenom = rs.getString("prenom");
                        Object[] rowData = {studentIdStr, studentNom, studentPrenom};
                        tableModel.addRow(rowData);

                        // Remove the input components
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();

                        // Create a JTable with the DefaultTableModel
                        table = new JTable(tableModel);

                        // Add the JTable to the panel
                        panel.add(new JScrollPane(table));

                        // Revalidate and repaint the panel to reflect the changes
                        panel.revalidate();
                        panel.repaint();
                    } else {
                        JOptionPane.showMessageDialog(panel, "No student found with ID " + enteredId, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
