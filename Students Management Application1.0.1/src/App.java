/**
 * Student Management Application
 *
 *
 * This application allows for the management of student records.
 * Developed by: BERCHID WALID
 * Version: 1.0
 * Date: 14/11/2023
 * Note: This is a small application that will be developed further in the future.
 *
 *
 */


import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class App extends JFrame implements ActionListener {

    public static void main(String[] args) {
        try {
            // Set FlatLaf look and feel
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Create and show the application window
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }

    JTabbedPane tabbedPane = new JTabbedPane();
    JMenuItem addMenu = new JMenuItem("Add Student");
    JMenu deleteMenu = new JMenu("Delete Student");
    JMenuItem updateMenu = new JMenuItem("Update Student");
    JMenu showMenu = new JMenu("Show Student");
    JMenuItem showAllItem = new JMenuItem("Show All Students");

    JMenuItem showSpecific = new JMenuItem("Show a Student");
    JMenuItem deleteAllItem = new JMenuItem("Delete All Students");
    JMenuItem deleteSpecific = new JMenuItem("Delete a Student");

    public App() {
        setTitle("Gestion Des Etudiantes");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String iconFilePath = "C:\\Users\\PC\\Desktop\\projects\\App To Add Students\\src\\wisd.png";
        File iconFile = new File(iconFilePath);

        if (iconFile.exists()) {
            ImageIcon icon = new ImageIcon(iconFile.getAbsolutePath());
            setIconImage(icon.getImage());
        } else {
            System.out.println("Icon file not found at the specified path: " + iconFilePath);
        }

        setJMenuBar(createMenuBar());


        getContentPane().add(tabbedPane);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");

        addMenu.setActionCommand("Add Student");

        fileMenu.add(addMenu);
        fileMenu.add(deleteMenu);
        fileMenu.add(updateMenu);
        fileMenu.add(showMenu);

        showMenu.add(showAllItem);
        showMenu.add(showSpecific);

        deleteMenu.add(deleteAllItem);
        deleteMenu.add(deleteSpecific);

        menuBar.add(fileMenu);

        // Add ActionListeners to the menu items
        addMenu.addActionListener(this);
        updateMenu.addActionListener(this);
        showAllItem.addActionListener(this);
        deleteAllItem.addActionListener(this);
        showSpecific.addActionListener(this);
        deleteSpecific.addActionListener(this);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Add Student")) {
            openInternalFrame("Add Student");
        } else if (e.getActionCommand().equalsIgnoreCase("Show All Students")) {
            openInternalFrame("Show All Students");
        } else if (e.getActionCommand().equalsIgnoreCase("Delete All Students")) {
            openInternalFrame("Delete All Students");
        } else if (e.getActionCommand().equalsIgnoreCase("Update Student")) {
            openInternalFrame("Update Student");
        } else if (e.getActionCommand().equalsIgnoreCase("Show a Student")) {
            openInternalFrame("Show a Student");
        } else if (e.getActionCommand().equalsIgnoreCase("Delete a Student")) {
            openInternalFrame("Delete a Student");
        }
    }

    private void openInternalFrame(String title) {
        JInternalFrame internalFrame = new JInternalFrame(title, true, true, true, true);
        internalFrame.setSize(400, 300);

        JPanel panel = new JPanel(new BorderLayout());

        switch (title) {
            case "Add Student":
                new AddStudent(panel);
                break;
            case "Show All Students":
                new ShowStudents(panel);
                break;
            case "Delete All Students":
                new DeleteStudents(panel);
                break;
            case "Update Student":
                new UpdateStudent(panel);
                break;
            case "Show a Student":
                new ShowSpecific(panel);
                break;
            case "Delete a Student":
                new DeleteSpecific(panel);
                break;
            default:
                break;
        }

        internalFrame.add(panel);
        tabbedPane.addTab(title, internalFrame.getContentPane());
        internalFrame.setVisible(true);
    }




}
