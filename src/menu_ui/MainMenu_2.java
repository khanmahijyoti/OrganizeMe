package menu_ui;


import Login.User;
import Login.UserInfoPrint;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public  class MainMenu_2 extends Component {

    private JButton accountButton;
    private JButton financeButton;
    private JButton scheduleButton;
    private JButton datesButton;
    private JButton studyButton;
    private JButton exitButton;
    private JPanel panel2;
    public static User user;

    public MainMenu_2(User user) {
       this.user= user;
        initializeComponents();
        initialize();


    }



    private void initializeComponents() {
        JFrame frame = new JFrame("User Login Form");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 250));
        frame.setResizable(false);
        frame.add(panel2);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    private void initialize() {
        accountButton.addActionListener(this::viewAccountInfo);
        financeButton.addActionListener(this::manageFinances);
        scheduleButton.addActionListener(this::scheduleManager);

        studyButton.addActionListener(this::studyPal);
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void viewAccountInfo(ActionEvent e) {
        UserInfoPrint userInfoPrint = new UserInfoPrint(user);  // Assuming 'user' is already defined in your scope
        userInfoPrint.loadUserInfo();  // Load or reload user info, if necessary

        // Build a displayable string from user info
        StringBuilder userInfoDetails = new StringBuilder();
        ArrayList<String> userInfo = userInfoPrint.getUserInfo();
        for (String detail : userInfo) {
            userInfoDetails.append(detail).append("\n");
        }

        // Show user info in a JOptionPane
        JOptionPane.showMessageDialog(this, userInfoDetails.toString(), "Account Information", JOptionPane.INFORMATION_MESSAGE);
    }


    private void manageFinances(ActionEvent e) {

           /*NFinancialManagement financeManagement;
        financeManagement = new FinancialManagement(user);
        financeManagement.run();*/

            // Implement or call a method for GUI

        }


    private void scheduleManager(ActionEvent e) {
        /*try {
            ScheduleManagement scheduleManagement = new ScheduleManagement(user);
            scheduleManagement.run();
            // Implement or call a method for GUI
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
            // Handle exception
        }*/
    }



    private void studyPal(ActionEvent e) {
        // Assuming pomodoro.pomodoro is properly setup to display its GUI
        new pomodoro.pomodoro();
    }

    public static void main(String[] args) {

        new MainMenu_2(user) ;
    }
}






