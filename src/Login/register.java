package Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class register extends JFrame {
    private JTextField enterUsernameTextField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JPasswordField confirmPasswordField = new JPasswordField(20);
    private JTextField firstNameTextField = new JTextField(20);
    private JTextField lastNameTextField = new JTextField(20);
    private JTextField dateOfBirthyyyyTextField = new JTextField(20);
    private JTextField placeOfBirthTextField = new JTextField(20);
    private JTextField addressTextField = new JTextField(20);
    private JTextField phoneNumberTextField = new JTextField(20);
    private JTextField emailTextField = new JTextField(20);
    private JComboBox<String> sexComboBox = new JComboBox<>(new String[] {"Male", "Female"});
    private JComboBox<String> maritalStatusComboBox = new JComboBox<>(new String[] {"Married", "Unmarried"});
    private JTextField nationalityTextField = new JTextField(20);
    private JTextField incomeTextField = new JTextField(20);
    private JTextField getDateOfBirthyyyyTextField;
    private JComboBox<String> currencyComboBox = new JComboBox<>(new String[] {"BDT", "$", "Euro"});

    public register() {

        setLayout(new GridLayout(0, 2)); // Grid layout for form elements

        add(new JLabel("Username:"));
        add(enterUsernameTextField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(new JLabel("Confirm Password:"));
        add(confirmPasswordField);
        add(new JLabel("First Name:"));
        add(firstNameTextField);
        add(new JLabel("Last Name:"));
        add(lastNameTextField);
        add(new JLabel("Date of Birth (yyyy-MM-dd):"));
        add(dateOfBirthyyyyTextField);
        add(new JLabel("Place of Birth:"));
        add(placeOfBirthTextField);
        add(new JLabel("Address:"));
        add(addressTextField);
        add(new JLabel("Phone Number:"));
        add(phoneNumberTextField);
        add(new JLabel("Email:"));
        add(emailTextField);
        add(new JLabel("Sex:"));
        add(sexComboBox);
        add(new JLabel("Marital Status:"));
        add(maritalStatusComboBox);
        add(new JLabel("Nationality:"));
        add(nationalityTextField);
        add(new JLabel("Income:"));
        add(incomeTextField);
        add(new JLabel("Currency:"));
        add(currencyComboBox);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(this::registerAction);
        add(registerButton);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public static void main() {
    }

    private void registerAction(ActionEvent event) {
        String username = enterUsernameTextField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String dob = dateOfBirthyyyyTextField.getText();
        String placeOfBirth = placeOfBirthTextField.getText();
        String address = addressTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();
        String sex = (String) sexComboBox.getSelectedItem();
        String maritalStatus = (String) maritalStatusComboBox.getSelectedItem();
        String nationality = nationalityTextField.getText();
        double income = Double.parseDouble(incomeTextField.getText());
        String currency = (String) currencyComboBox.getSelectedItem();

        // Convert DOB to Date
        Date dateOfBirth = null;
        try {
            dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Invalid date format.");
            return;
        }

        // Check if password is strong and matches
        if (!password.equals(confirmPassword) || !isStrongPassword(password)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match or are not strong enough.");
        } else if (!isValidPhoneNumber(phoneNumber)) {
            JOptionPane.showMessageDialog(this, "Invalid phone number. Must be exactly 11 digits.");
        } else if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email. Must contain '@' and end with '.com'.");
        } else {
            User user = new User(username, password);
            UserInfo userInfo = new UserInfo(username, firstName, lastName, dateOfBirth, placeOfBirth, address, phoneNumber, email, sex, maritalStatus, nationality, (int)income, currency);
            CreateUser createUser = new CreateUser(user, userInfo);

            try {
                if (createUser.checkUser() && createUser.createAccount()) {
                    JOptionPane.showMessageDialog(this, "Account created successfully.");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Account creation failed or user already exists.");
                }
            } catch (Exception var24) {
                JOptionPane.showMessageDialog(this, "An error occurred: " + var24.getMessage());
            }
        }
    }



    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{11}");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[^@\\s]+@[^@\\s]+\\.com$");
    }
    public static boolean isStrongPassword(String password) {
        return password.matches(".*[A-Z].*") && password.matches(".*[a-z].*") && password.matches(".*\\d.*") && password.length() >= 6;
    }

    public static void main(String[] args) {
        new register();
    }
}
