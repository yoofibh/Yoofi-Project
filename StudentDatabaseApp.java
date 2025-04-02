import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class StudentDatabaseApp extends JFrame {
    private JTextField nameField, ageField, courseField;
    private JTextArea outputArea;

    public StudentDatabaseApp() {
        setTitle("Student Database Manager");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Student Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);
        inputPanel.add(new JLabel("Student Course:"));
        courseField = new JTextField();
        inputPanel.add(courseField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent());
        inputPanel.add(addButton);

        JButton viewButton = new JButton("View Students");
        viewButton.addActionListener(e -> viewStudents());
        inputPanel.add(viewButton);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    private void addStudent() {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String course = courseField.getText();

            FileWriter writer = new FileWriter("students.txt", true);
            writer.write(name + "," + age + "," + course + "\n");
            writer.close();

            outputArea.append("Student added successfully!\n");
            nameField.setText("");
            ageField.setText("");
            courseField.setText("");
        } catch (NumberFormatException e) {
            outputArea.append("Invalid age format. Please enter a number.\n");
        } catch (IOException e) {
            outputArea.append("Error adding student: " + e.getMessage() + "\n");
        }
    }

    private void viewStudents() {
        try {
            FileReader reader = new FileReader("students.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);

            String students = "";
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                students += "Name: " + parts[0] + ", Age: " + parts[1] + ", Course: " + parts[2] + "\n";
            }

            bufferedReader.close();
            reader.close();

            outputArea.setText("List of Students:\n" + students);
        } catch (FileNotFoundException e) {
            outputArea.setText("No students found!");
        } catch (IOException e) {
            outputArea.setText("Error viewing students: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDatabaseApp());
    }
}