import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EventManagementApp extends JFrame {
    private JTextField eventNameField, eventDateField, eventVenueField, eventDescriptionField;
    private JButton createEventButton;
    private Connection connection;

    public EventManagementApp() {
        super("Event Management Application");

        // Initialize GUI components
        eventNameField = new JTextField(20);
        eventDateField = new JTextField(10);
        eventVenueField = new JTextField(20);
        eventDescriptionField = new JTextField(30);
        createEventButton = new JButton("Create Event");

        // Set up layout
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Event Name:"));
        add(eventNameField);
        add(new JLabel("Event Date (YYYY-MM-DD):"));
        add(eventDateField);
        add(new JLabel("Event Venue:"));
        add(eventVenueField);
        add(new JLabel("Event Description:"));
        add(eventDescriptionField);
        add(new JLabel()); // Empty label for spacing
        add(createEventButton);

        // Initialize database connection and create events table
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/event_management?useSSL=false", "root", "nandini");
            createEventsTable(); // Call the method to create the "events" table
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to connect to the database!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Set up event handler for Create Event button
        createEventButton.addActionListener(e -> createEvent());

        // Set window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createEvent() {
        String eventName = eventNameField.getText().trim();
        String eventDate = eventDateField.getText().trim();
        String eventVenue = eventVenueField.getText().trim();
        String eventDescription = eventDescriptionField.getText().trim();

        if (eventName.isEmpty() || eventDate.isEmpty() || eventVenue.isEmpty() || eventDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO events (eventName, eventDate, eventVenue, eventDescription) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, eventName);
            preparedStatement.setString(2, eventDate);
            preparedStatement.setString(3, eventVenue);
            preparedStatement.setString(4, eventDescription);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Event created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create event!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Print the stack trace for detailed error information
            JOptionPane.showMessageDialog(this, "Failed to create event: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to create the events table
    private void createEventsTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE events (eventName VARCHAR(100), eventDate DATE, eventVenue VARCHAR(100), eventDescription VARCHAR(255))");
            System.out.println("Events table created successfully.");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to create events table: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        eventNameField.setText("");
        eventDateField.setText("");
        eventVenueField.setText("");
        eventDescriptionField.setText("");
    }

    public static void main(String[] args) {
        new EventManagementApp();
    }
}
