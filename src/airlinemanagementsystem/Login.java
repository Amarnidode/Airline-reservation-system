package airlinemanagementsystem;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/airlinemanagementsystem"; // Replace with your DB URL
    private static final String DB_USER = "root"; // Replace with your DB username
    private static final String DB_PASS = "root"; // Replace with your DB password

    public static void main(String[] args) {
        // Create main frame
        JFrame frame = new JFrame("User and Admin Login");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the frame
        frame.setLocationRelativeTo(null);  // This will center the frame

        // Set the layout manager
        frame.setLayout(new BorderLayout());

        // Set background color of the frame
        frame.getContentPane().setBackground(new Color(204, 229, 255)); // Light blue background

        // Create a panel for the form with GridBagLayout to center components
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(204, 229, 255)); // Light blue background for the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Components for role selection
        JLabel roleLabel = new JLabel("Select Role:");
        panel.add(roleLabel, gbc);

        JComboBox<String> roleBox = new JComboBox<>(new String[]{"User", "Admin"});
        gbc.gridx = 1;
        panel.add(roleBox, gbc);

        // Login Form Components
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel("Username:");
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passLabel = new JLabel("Password:");
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; 
        panel.add(loginButton, gbc);

        JLabel messageLabel = new JLabel("");
        gbc.gridx = 1;
        panel.add(messageLabel, gbc);

        // Register button, placed below the login button
        JButton registerButton = new JButton("Register");
        gbc.gridx = 0;   // Align it to the first column
        gbc.gridy = 4;   // Move it down to the next row (below the Login button)
        gbc.gridwidth = 2; // Make it span across both columns
        panel.add(registerButton, gbc);

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String role = roleBox.getSelectedItem().toString().toLowerCase();

            if (loginUser(username, password, role)) {
                messageLabel.setText("Login successful as " + role + "!");
                showRoleSpecificDashboard(frame, role, username);
            } else {
                messageLabel.setText("Login failed! Invalid credentials.");
                // Open Register if login fails
                openRegisterPage(frame);
            }
        });

        // Register button action
        registerButton.addActionListener(e -> {
            openRegisterPage(frame);
        });

        frame.setVisible(true);
    }

    // Method to validate login credentials
    private static boolean loginUser(String username, String password, String role) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            if ("admin".equals(role)) {
                // Check admin credentials
                if ("admin".equals(username) && "admin123".equals(password)) {
                    return true; // Admin login successful
                } else {
                    return false; // Invalid admin credentials
                }
            } else {
                // Check user credentials
                try (PreparedStatement stmt = conn.prepareStatement(
                        "SELECT * FROM Users WHERE username = ? AND password = ? AND role = ?")) {

                    stmt.setString(1, username);
                    stmt.setString(2, password); // In production, use hashed passwords
                    stmt.setString(3, role);
                    ResultSet rs = stmt.executeQuery();

                    return rs.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to display role-specific dashboard
    private static void showRoleSpecificDashboard(JFrame frame, String role, String username) {
        frame.dispose(); // Close the login frame

        if ("admin".equals(role)) {
            new Home(); // Open the admin home window
        } else {
            new UserHome(); // Open the existing UserHome page when the user logs in
        }
    }

    // Method to open registration page
    private static void openRegisterPage(JFrame frame) {
        frame.dispose(); // Close the login page

        JFrame registerFrame = new JFrame("User Registration");
        registerFrame.setSize(500, 400);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setLayout(new BorderLayout());

        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(new Color(204, 229, 255)); // Light blue background for the panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding between components

        // Registration Form Components
        JLabel regUserLabel = new JLabel("Username:");
        registerPanel.add(regUserLabel, gbc);

        JTextField regUserField = new JTextField(15);
        gbc.gridx = 1;
        registerPanel.add(regUserField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel regPassLabel = new JLabel("Password:");
        registerPanel.add(regPassLabel, gbc);

        JPasswordField regPassField = new JPasswordField(15);
        gbc.gridx = 1;
        registerPanel.add(regPassField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel regRoleLabel = new JLabel("Role (user):");
        registerPanel.add(regRoleLabel, gbc);

        JTextField regRoleField = new JTextField(15);
        gbc.gridx = 1;
        registerPanel.add(regRoleField, gbc);

        JButton registerSubmitButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        registerPanel.add(registerSubmitButton, gbc);

        JLabel registerMessage = new JLabel("");
        gbc.gridx = 1;
        registerPanel.add(registerMessage, gbc);

        // Add the register panel to the frame
        registerFrame.add(registerPanel, BorderLayout.CENTER);

        // Register submit action
        registerSubmitButton.addActionListener(e -> {
            String username = regUserField.getText();
            String password = new String(regPassField.getPassword());
            String role = regRoleField.getText();

            if (registerUser(username, password, role)) {
                registerMessage.setText("Registration successful! Please login.");
                // Return to login screen after registration
                registerFrame.dispose();
                Login.main(null);
            }
            else {
                registerMessage.setText("Registration failed! Try again.");
            }
        });

        registerFrame.setVisible(true);
    }

    // Method to register a new user
    private static boolean registerUser(String username, String password, String role) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            // Insert user into the database
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)")) {

                stmt.setString(1, username);
                stmt.setString(2, password); // In production, use hashed passwords
                stmt.setString(3, role);
                int rowsAffected = stmt.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
