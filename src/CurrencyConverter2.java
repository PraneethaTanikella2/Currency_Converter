import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class CurrencyConverter2 {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CurrencyConverter";
    private static final String USER = "root"; // replace with your MySQL username
    private static final String PASS = "root"; // replace with your MySQL password
    private static HashMap<String, String> users = new HashMap<>(); // Simulated user storage

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurrencyConverter2::showLoginSignupPage);
    }

    private static void showLoginSignupPage() {
        JFrame frame = new JFrame("Login or Signup");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            private Image backgroundImage = new ImageIcon("C:\\Users\\sweet\\IdeaProjects\\CurrencyConvertor\\background.jpg").getImage(); // Replace with your image path

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Fill the whole panel
            }
        };
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Set font for larger text
        Font font = new Font("Arial", Font.PLAIN, 18);

        // Username and password fields
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        usernameLabel.setForeground(Color.WHITE); // Set text color to white
        JTextField usernameField = new JTextField(10);
        usernameField.setFont(font);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(font);
        passwordLabel.setForeground(Color.WHITE); // Set text color to white
        JPasswordField passwordField = new JPasswordField(10);
        passwordField.setFont(font);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Login and Signup buttons
        JButton loginButton = new JButton("Login");
        loginButton.setFont(font);
        JButton signupButton = new JButton("Signup");
        signupButton.setFont(font);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(signupButton, gbc);

        frame.add(panel);
        frame.setVisible(true);

        // Action listeners for login and signup buttons
        loginButton.addActionListener(e -> handleLogin(usernameField, passwordField, frame));
        signupButton.addActionListener(e -> handleSignup(usernameField, passwordField));
    }

    private static void handleLogin(JTextField usernameField, JPasswordField passwordField, JFrame frame) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (users.containsKey(username) && users.get(username).equals(password)) {
            frame.dispose();
            SwingUtilities.invokeLater(CurrencyConverter2::showCurrencyConverter);
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid credentials. Please try again.");
            usernameField.setText(""); // Clear the username field
            passwordField.setText(""); // Clear the password field
        }
    }

    private static void handleSignup(JTextField usernameField, JPasswordField passwordField) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists.");
        } else {
            users.put(username, password);
            JOptionPane.showMessageDialog(null, "Signup successful! You can now login.");
        }
        usernameField.setText(""); // Clear the username field
        passwordField.setText(""); // Clear the password field
    }

    private static void showCurrencyConverter() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Set font for larger text
        Font font = new Font("Arial", Font.PLAIN, 18);

        // Create a panel with a background image
        JPanel panel = new JPanel() {
            private Image backgroundImage = new ImageIcon("C:\\Users\\sweet\\IdeaProjects\\CurrencyConvertor\\background.jpg").getImage(); // Replace with your image path

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this); // Fill the whole panel
            }
        };
        panel.setLayout(new GridBagLayout());

        // Labels and input fields
        JLabel amountLabel = new JLabel("Enter amount:");
        amountLabel.setFont(font);
        amountLabel.setForeground(Color.WHITE); // Set text color to white
        JTextField amountField = new JTextField(10);
        amountField.setFont(font);

        // Combo boxes for currency selection
        JComboBox<String> sourceCurrencyComboBox = new JComboBox<>(new String[]{
                "INR", "USD", "EUR", "GBP", "JPY", "AUD",
                "CAD", "BRL", "ZAR", "MXN", "CNY", "NZD",
                "CHF", "KRW", "RUB", "TRY", "SGD", "HKD",
                "SEK", "NOK", "DKK", "THB", "MYR"
        });
        sourceCurrencyComboBox.setFont(font);

        JComboBox<String> targetCurrencyComboBox = new JComboBox<>(new String[]{
                "INR", "USD", "EUR", "GBP", "JPY", "AUD",
                "CAD", "BRL", "ZAR", "MXN", "CNY", "NZD",
                "CHF", "KRW", "RUB", "TRY", "SGD", "HKD",
                "SEK", "NOK", "DKK", "THB", "MYR"
        });
        targetCurrencyComboBox.setFont(font);

        JButton convertButton = new JButton("Convert");
        convertButton.setFont(font);
        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setFont(font);
        resultLabel.setForeground(Color.WHITE); // Set text color to white

        // Layout components
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(amountLabel, gbc);

        gbc.gridx = 1;
        panel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("From:"){{setFont(font); setForeground(Color.WHITE);}}, gbc);

        gbc.gridx = 1;
        panel.add(sourceCurrencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("To:"){{setFont(font); setForeground(Color.WHITE);}}, gbc);

        gbc.gridx = 1;
        panel.add(targetCurrencyComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(convertButton, gbc);

        gbc.gridy = 4;
        panel.add(resultLabel, gbc);

        frame.add(panel);
        frame.setVisible(true);

        // Conversion logic for demonstration
        convertButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String sourceCurrency = (String) sourceCurrencyComboBox.getSelectedItem();
                String targetCurrency = (String) targetCurrencyComboBox.getSelectedItem();

                double sourceRate = getExchangeRate(sourceCurrency);
                double targetRate = getExchangeRate(targetCurrency);

                double convertedAmount = amount * (targetRate / sourceRate);
                resultLabel.setText("Result: " + String.format("%.2f", convertedAmount) + " " + targetCurrency);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid amount.");
            } catch (Exception ex) {
                resultLabel.setText("Database error: " + ex.getMessage());
            }
        });
    }

    private static double getExchangeRate(String currencyCode) throws Exception {
        double exchangeRate = 0.0;
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        String query = "SELECT exchangeRate FROM ExchangeRates WHERE currencyCode = ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, currencyCode);
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            exchangeRate = rs.getDouble("exchangeRate");
        }

        rs.close();
        pstmt.close();
        conn.close();
        return exchangeRate;
    }
}
