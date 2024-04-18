package Login;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static menu_ui.MainMenu_2.user;

public class ShowUserInfo extends JFrame {

    public ShowUserInfo() {
        // Frame setup
        setTitle("Text File Viewer");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // Make text area non-editable
        JScrollPane scrollPane = new JScrollPane(textArea);  // Add text area to scroll pane
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Load text file content
        loadTextFromFile(textArea, "C:\\OrganizeMe\\Data\\"+ user.getUsername()+"\\UserInfo.txt");  // Specify the file path

        // Display the frame
        setVisible(true);
    }

    private void loadTextFromFile(JTextArea textArea, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");  // Append each line to the text area
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load the file.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ShowUserInfo::new);
    }
}
