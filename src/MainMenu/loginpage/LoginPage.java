package MainMenu.loginpage;

import Login.LoginSystem;
import Login.User;

import menu_ui.MainMenu_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginPage {
    private JPanel panel1;
    private JTextField textField1;

    private JPasswordField passwordField1;
    private JButton loginButton;
    public LoginPage() {

        JFrame frame = new JFrame("User Login Form");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 250));
        frame.setResizable(false);
        frame.add(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a User object from the input fields
                User user = new User(textField1.getText(), new String(passwordField1.getPassword()));

                // Initialize LoginSystem with the created User
                LoginSystem loginSystem = new LoginSystem(user);

                // Check if the account exists and password is correct
                if (loginSystem.checkAccount() && loginSystem.checkPassword()) {
                    // Success Logic, maybe open a new JFrame or show a success message
                    JOptionPane.showMessageDialog(panel1, "Login Successful!");
                    // Possibly hide the login window or navigate to another part of the application here
                    frame.dispose();


                    MainMenu_2 menu = new MainMenu_2(user);

                        new MainMenu_2(user);


                } else {
                    // Failure logic
                    JOptionPane.showMessageDialog(panel1, "Login Failed. Please check your username and password.");
                }
            }

    });
    }


    public static void main() {
        new LoginPage();
    }

}
