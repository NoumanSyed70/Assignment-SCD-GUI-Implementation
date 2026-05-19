package library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// CUSTOM EXCEPTIONS 

class EmptyFieldException extends Exception {
    public EmptyFieldException(String msg) {
        super(msg);
    }
}

class InvalidRollNumberException extends Exception {
    public InvalidRollNumberException(String msg) {
        super(msg);
    }
}

class InvalidDateException extends Exception {
    public InvalidDateException(String msg) {
        super(msg);
    }
}

class NullSelectionException extends Exception {
    public NullSelectionException(String msg) {
        super(msg);
    }
}

// Mandatory extra custom exception
class BookNotAvailableException extends Exception {
    public BookNotAvailableException(String msg) {
        super(msg);
    }
}

//MAIN CLASS

public class Library extends JFrame {

    JTextField nameField, rollField, issueDateField, returnDateField;
    JTextArea remarksArea;

    JComboBox<String> bookBox;
    JRadioButton newBook, oldBook;
    ButtonGroup bg;

    JButton issueBtn, resetBtn, exitBtn;

    public Library() {

        setTitle("Library Issue Control Panel");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        // MAIN CONTAINER (UNIQUE SPLIT DESIGN)
        setLayout(new BorderLayout());

        //LEFT PANEL (DECORATIVE)
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(25, 42, 86));
        leftPanel.setLayout(new BorderLayout());

        JLabel title = new JLabel("<html><center>LIBRARY<br>ISSUE SYSTEM</center></html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        leftPanel.add(title, BorderLayout.CENTER);

        // RIGHT PANEL (FORM) 
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBackground(Color.WHITE);

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Labels + Fields
        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(30, 20, 150, 25);
        nameLabel.setFont(labelFont);

        nameField = new JTextField();
        nameField.setBounds(180, 20, 200, 25);

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setBounds(30, 60, 150, 25);
        rollLabel.setFont(labelFont);

        rollField = new JTextField();
        rollField.setBounds(180, 60, 200, 25);

        JLabel bookLabel = new JLabel("Book Category:");
        bookLabel.setBounds(30, 100, 150, 25);
        bookLabel.setFont(labelFont);

        bookBox = new JComboBox<>(new String[]{"Select", "Programming", "AI", "Databases", "Networking"});
        bookBox.setBounds(180, 100, 200, 25);

        JLabel typeLabel = new JLabel("Book Type:");
        typeLabel.setBounds(30, 140, 150, 25);
        typeLabel.setFont(labelFont);

        newBook = new JRadioButton("New Edition");
        oldBook = new JRadioButton("Old Edition");

        newBook.setBounds(180, 140, 120, 25);
        oldBook.setBounds(300, 140, 120, 25);

        bg = new ButtonGroup();
        bg.add(newBook);
        bg.add(oldBook);

        JLabel issueLabel = new JLabel("Issue Date (dd-mm-yyyy):");
        issueLabel.setBounds(30, 180, 180, 25);
        issueLabel.setFont(labelFont);

        issueDateField = new JTextField();
        issueDateField.setBounds(220, 180, 160, 25);

        JLabel returnLabel = new JLabel("Return Date (dd-mm-yyyy):");
        returnLabel.setBounds(30, 220, 200, 25);
        returnLabel.setFont(labelFont);

        returnDateField = new JTextField();
        returnDateField.setBounds(220, 220, 160, 25);

        JLabel remarksLabel = new JLabel("Remarks:");
        remarksLabel.setBounds(30, 260, 150, 25);
        remarksLabel.setFont(labelFont);

        remarksArea = new JTextArea();
        remarksArea.setBounds(180, 260, 250, 60);
        remarksArea.setLineWrap(true);
        remarksArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // Buttons
        issueBtn = new JButton("ISSUE BOOK");
        resetBtn = new JButton("RESET");
        exitBtn = new JButton("EXIT");

        issueBtn.setBounds(30, 340, 150, 35);
        resetBtn.setBounds(190, 340, 100, 35);
        exitBtn.setBounds(300, 340, 100, 35);

        issueBtn.setBackground(new Color(46, 204, 113));
        issueBtn.setForeground(Color.BLACK);

        resetBtn.setBackground(new Color(241, 196, 15));
        resetBtn.setForeground(Color.BLACK);

        exitBtn.setBackground(new Color(231, 76, 60));
        exitBtn.setForeground(Color.BLACK);

        // Extra Styling for Better Visibility
        issueBtn.setFocusPainted(false);
        resetBtn.setFocusPainted(false);
        exitBtn.setFocusPainted(false);

        issueBtn.setFont(new Font("Arial", Font.BOLD, 13));
        resetBtn.setFont(new Font("Arial", Font.BOLD, 13));
        exitBtn.setFont(new Font("Arial", Font.BOLD, 13));

        // Add components
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        formPanel.add(rollLabel);
        formPanel.add(rollField);

        formPanel.add(bookLabel);
        formPanel.add(bookBox);

        formPanel.add(typeLabel);
        formPanel.add(newBook);
        formPanel.add(oldBook);

        formPanel.add(issueLabel);
        formPanel.add(issueDateField);

        formPanel.add(returnLabel);
        formPanel.add(returnDateField);

        formPanel.add(remarksLabel);
        formPanel.add(remarksArea);

        formPanel.add(issueBtn);
        formPanel.add(resetBtn);
        formPanel.add(exitBtn);

        add(leftPanel, BorderLayout.WEST);
        add(formPanel, BorderLayout.CENTER);

        leftPanel.setPreferredSize(new Dimension(250, 0));

        // ================= ACTIONS =================

        issueBtn.addActionListener(e -> {

            try {
                validateForm();

                JOptionPane.showMessageDialog(this,
                        "Book Issued Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (EmptyFieldException | InvalidRollNumberException |
                     InvalidDateException | NullSelectionException |
                     BookNotAvailableException ex) {

                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

            } finally {
                System.out.println("Operation Completed (finally block executed)");
            }
        });

        resetBtn.addActionListener(e -> resetForm());

        exitBtn.addActionListener(e -> System.exit(0));
    }

    //VALIDATION LOGIC 

    private void validateForm() throws EmptyFieldException,
            InvalidRollNumberException,
            InvalidDateException,
            NullSelectionException,
            BookNotAvailableException {

        // Empty fields
        if (nameField.getText().trim().isEmpty() ||
                rollField.getText().trim().isEmpty() ||
                issueDateField.getText().trim().isEmpty() ||
                returnDateField.getText().trim().isEmpty()) {
            throw new EmptyFieldException("All required fields must be filled!");
        }

        // Roll number validation
        String roll = rollField.getText().trim();
        if (!roll.matches("\\d+")) {
            throw new InvalidRollNumberException("Roll number must contain only digits!");
        }

        // Null selection check
        if (bookBox.getSelectedIndex() == 0 ||
                (!newBook.isSelected() && !oldBook.isSelected())) {
            throw new NullSelectionException("Please select Book Category and Book Type!");
        }

        // Date validation
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);

        try {
            Date issue = sdf.parse(issueDateField.getText().trim());
            Date ret = sdf.parse(returnDateField.getText().trim());

            if (ret.before(issue)) {
                throw new InvalidDateException("Return date cannot be earlier than issue date!");
            }

        } catch (ParseException e) {
            throw new InvalidDateException("Invalid date format! Use dd-MM-yyyy");
        }

        // Custom logic exception
        if (bookBox.getSelectedItem().equals("Networking")) {
            throw new BookNotAvailableException("Networking books are currently unavailable!");
        }
    }

    private void resetForm() {
        nameField.setText("");
        rollField.setText("");
        issueDateField.setText("");
        returnDateField.setText("");
        remarksArea.setText("");
        bookBox.setSelectedIndex(0);
        bg.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Library().setVisible(true));
    }
}