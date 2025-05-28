import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class HotelBookingSystem extends JFrame {
    private ArrayList<Room> rooms;
    private ArrayList<Booking> bookings;
    private JTable roomTable;
    private JTable bookingTable;
    private DefaultTableModel roomTableModel;
    private DefaultTableModel bookingTableModel;
    
    // GUI Components
    private JTextField customerNameField;
    private JTextField customerEmailField;
    private JTextField customerPhoneField;
    private JTextField roomNumberField;
    private JComboBox<String> roomTypeCombo;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    
    public HotelBookingSystem() {
        initializeData();
        initializeGUI();
        setupEventListeners();
    }
    
    private void initializeData() {
        rooms = new ArrayList<>();
        bookings = new ArrayList<>();
        
        // Initialize sample rooms
        rooms.add(new Room(101, "Single", 1500.0, true));
        rooms.add(new Room(102, "Single", 1500.0, true));
        rooms.add(new Room(201, "Double", 2500.0, true));
        rooms.add(new Room(202, "Double", 2500.0, true));
        rooms.add(new Room(301, "Suite", 5000.0, true));
        rooms.add(new Room(302, "Suite", 5000.0, true));
        rooms.add(new Room(401, "Deluxe", 3500.0, true));
        rooms.add(new Room(402, "Deluxe", 3500.0, true));
    }
    
    private void initializeGUI() {
        setTitle("Hotel Room Booking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Booking Tab
        JPanel bookingPanel = createBookingPanel();
        tabbedPane.addTab("Make Booking", bookingPanel);
        
        // View Rooms Tab
        JPanel roomsPanel = createRoomsPanel();
        tabbedPane.addTab("View Rooms", roomsPanel);
        
        // View Bookings Tab
        JPanel bookingsPanel = createBookingsPanel();
        tabbedPane.addTab("View Bookings", bookingsPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Status bar
        JLabel statusBar = new JLabel("Ready");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusBar, BorderLayout.SOUTH);
        
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }
    
    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        JLabel titleLabel = new JLabel("Hotel Room Booking");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        
        // Customer Information
        JLabel customerInfoLabel = new JLabel("Customer Information");
        customerInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(customerInfoLabel, gbc);
        
        gbc.gridwidth = 1;
        
        // Customer Name
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Customer Name:"), gbc);
        gbc.gridx = 1;
        customerNameField = new JTextField(20);
        panel.add(customerNameField, gbc);
        
        // Customer Email
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        customerEmailField = new JTextField(20);
        panel.add(customerEmailField, gbc);
        
        // Customer Phone
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        customerPhoneField = new JTextField(20);
        panel.add(customerPhoneField, gbc);
        
        // Room Information
        JLabel roomInfoLabel = new JLabel("Room Information");
        roomInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(roomInfoLabel, gbc);
        
        gbc.gridwidth = 1;
        
        // Room Type
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(new JLabel("Room Type:"), gbc);
        gbc.gridx = 1;
        roomTypeCombo = new JComboBox<>(new String[]{"Single", "Double", "Suite", "Deluxe"});
        panel.add(roomTypeCombo, gbc);
        
        // Check-in Date
        gbc.gridx = 0; gbc.gridy = 7;
        panel.add(new JLabel("Check-in Date:"), gbc);
        gbc.gridx = 1;
        checkInSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor checkInEditor = new JSpinner.DateEditor(checkInSpinner, "dd/MM/yyyy");
        checkInSpinner.setEditor(checkInEditor);
        panel.add(checkInSpinner, gbc);
        
        // Check-out Date
        gbc.gridx = 0; gbc.gridy = 8;
        panel.add(new JLabel("Check-out Date:"), gbc);
        gbc.gridx = 1;
        checkOutSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor checkOutEditor = new JSpinner.DateEditor(checkOutSpinner, "dd/MM/yyyy");
        checkOutSpinner.setEditor(checkOutEditor);
        panel.add(checkOutSpinner, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton bookButton = new JButton("Book Room");
        JButton clearButton = new JButton("Clear");
        JButton checkAvailabilityButton = new JButton("Check Availability");
        
        bookButton.setBackground(new Color(46, 125, 50));
        bookButton.setForeground(Color.WHITE);
        
        buttonPanel.add(checkAvailabilityButton);
        buttonPanel.add(bookButton);
        buttonPanel.add(clearButton);
        
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Available Rooms", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Room Number", "Room Type", "Price (₹)", "Status"};
        roomTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        roomTable = new JTable(roomTableModel);
        roomTable.setRowHeight(25);
        roomTable.getTableHeader().setBackground(new Color(63, 81, 181));
        roomTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(roomTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshButton.addActionListener(e -> updateRoomTable());
        
        return panel;
    }
    
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Current Bookings", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Table
        String[] columnNames = {"Booking ID", "Customer Name", "Room Number", "Check-in", "Check-out", "Total Amount"};
        bookingTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        bookingTable = new JTable(bookingTableModel);
        bookingTable.setRowHeight(25);
        bookingTable.getTableHeader().setBackground(new Color(63, 81, 181));
        bookingTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshButton = new JButton("Refresh");
        JButton cancelButton = new JButton("Cancel Booking");
        
        cancelButton.setBackground(new Color(244, 67, 54));
        cancelButton.setForeground(Color.WHITE);
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        refreshButton.addActionListener(e -> updateBookingTable());
        cancelButton.addActionListener(e -> cancelBooking());
        
        return panel;
    }
    
    private void setupEventListeners() {
        // Book Room button
        JButton bookButton = findButtonByText("Book Room");
        if (bookButton != null) {
            bookButton.addActionListener(e -> bookRoom());
        }
        
        // Clear button
        JButton clearButton = findButtonByText("Clear");
        if (clearButton != null) {
            clearButton.addActionListener(e -> clearFields());
        }
        
        // Check Availability button
        JButton checkButton = findButtonByText("Check Availability");
        if (checkButton != null) {
            checkButton.addActionListener(e -> checkAvailability());
        }
        
        // Update tables initially
        updateRoomTable();
        updateBookingTable();
    }
    
    private JButton findButtonByText(String text) {
        return findButtonInContainer(this, text);
    }
    
    private JButton findButtonInContainer(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals(text)) {
                return (JButton) component;
            } else if (component instanceof Container) {
                JButton found = findButtonInContainer((Container) component, text);
                if (found != null) return found;
            }
        }
        return null;
    }
    
    private void bookRoom() {
        try {
            // Validate input
            if (customerNameField.getText().trim().isEmpty() ||
                customerEmailField.getText().trim().isEmpty() ||
                customerPhoneField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all customer information fields.", 
                                            "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String roomType = (String) roomTypeCombo.getSelectedItem();
            Date checkIn = (Date) checkInSpinner.getValue();
            Date checkOut = (Date) checkOutSpinner.getValue();
            
            if (checkOut.before(checkIn) || checkOut.equals(checkIn)) {
                JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date.", 
                                            "Date Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Find available room
            Room availableRoom = findAvailableRoom(roomType);
            if (availableRoom == null) {
                JOptionPane.showMessageDialog(this, "No available rooms of type: " + roomType, 
                                            "No Availability", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Calculate total amount
            long diffInMillies = Math.abs(checkOut.getTime() - checkIn.getTime());
            long days = diffInMillies / (24 * 60 * 60 * 1000);
            if (days == 0) days = 1; // Minimum 1 day
            double totalAmount = availableRoom.getPrice() * days;
            
            // Create booking
            Customer customer = new Customer(customerNameField.getText().trim(),
                                           customerEmailField.getText().trim(),
                                           customerPhoneField.getText().trim());
            
            Booking booking = new Booking(generateBookingId(), customer, availableRoom, 
                                        checkIn, checkOut, totalAmount);
            
            bookings.add(booking);
            availableRoom.setAvailable(false);
            
            // Show confirmation
            String message = String.format("Booking Confirmed!\n\nBooking ID: %s\nRoom: %d (%s)\nCustomer: %s\nDays: %d\nTotal Amount: ₹%.2f",
                                         booking.getBookingId(), availableRoom.getRoomNumber(), 
                                         availableRoom.getRoomType(), customer.getName(), days, totalAmount);
            
            JOptionPane.showMessageDialog(this, message, "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
            
            // Clear fields and update tables
            clearFields();
            updateRoomTable();
            updateBookingTable();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error creating booking: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void checkAvailability() {
        String roomType = (String) roomTypeCombo.getSelectedItem();
        long availableCount = rooms.stream()
                                  .filter(room -> room.getRoomType().equals(roomType) && room.isAvailable())
                                  .count();
        
        JOptionPane.showMessageDialog(this, 
                                    String.format("Available %s rooms: %d", roomType, availableCount),
                                    "Room Availability", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void cancelBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.", 
                                        "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String bookingId = (String) bookingTableModel.getValueAt(selectedRow, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
                                                  "Are you sure you want to cancel booking " + bookingId + "?",
                                                  "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Find and remove booking
            Booking bookingToRemove = null;
            for (Booking booking : bookings) {
                if (booking.getBookingId().equals(bookingId)) {
                    bookingToRemove = booking;
                    booking.getRoom().setAvailable(true); // Make room available again
                    break;
                }
            }
            
            if (bookingToRemove != null) {
                bookings.remove(bookingToRemove);
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.", 
                                            "Cancellation Confirmed", JOptionPane.INFORMATION_MESSAGE);
                updateRoomTable();
                updateBookingTable();
            }
        }
    }
    
    private Room findAvailableRoom(String roomType) {
        return rooms.stream()
                   .filter(room -> room.getRoomType().equals(roomType) && room.isAvailable())
                   .findFirst()
                   .orElse(null);
    }
    
    private String generateBookingId() {
        return "BK" + System.currentTimeMillis();
    }
    
    private void clearFields() {
        customerNameField.setText("");
        customerEmailField.setText("");
        customerPhoneField.setText("");
        checkInSpinner.setValue(new Date());
        checkOutSpinner.setValue(new Date());
    }
    
    private void updateRoomTable() {
        roomTableModel.setRowCount(0);
        for (Room room : rooms) {
            Object[] row = {
                room.getRoomNumber(),
                room.getRoomType(),
                String.format("₹%.2f", room.getPrice()),
                room.isAvailable() ? "Available" : "Occupied"
            };
            roomTableModel.addRow(row);
        }
    }
    
    private void updateBookingTable() {
        bookingTableModel.setRowCount(0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Booking booking : bookings) {
            Object[] row = {
                booking.getBookingId(),
                booking.getCustomer().getName(),
                booking.getRoom().getRoomNumber(),
                dateFormat.format(booking.getCheckInDate()),
                dateFormat.format(booking.getCheckOutDate()),
                String.format("₹%.2f", booking.getTotalAmount())
            };
            bookingTableModel.addRow(row);
        }
    }
    
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
        new HotelBookingSystem().setVisible(true);
    });
}
}
