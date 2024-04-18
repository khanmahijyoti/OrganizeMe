package ui.java;

import Login.register;
import MainMenu.loginpage.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AppMain extends JFrame {
    public AppMain() {
        // Frame settings
        setTitle("OrganizeMe");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Main panel with box layout for vertical stacking
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create buttons
        JButton loginButton = new JButton("Log in");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        // Add action listeners
        loginButton.addActionListener(this::loginAction);
        registerButton.addActionListener(this::registerAction);
        exitButton.addActionListener(e -> System.exit(0));

        // Panel to center buttons vertically
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Space between buttons
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Space between buttons
        buttonPanel.add(exitButton);

        // Align panel items in the center horizontally
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add glue before and after the button panel to center it vertically
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        // Add main panel to the frame
        getContentPane().add(mainPanel);
    }

    private void loginAction(ActionEvent e) {
        LoginPage.main();
    }

    private void registerAction(ActionEvent e) {
        new register();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppMain appMain = new AppMain();
            appMain.setVisible(true);
        });
    }
}
