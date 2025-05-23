


package airlinemanagementsystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class Pay extends JFrame implements ActionListener {
    JLabel lblCardNumber, lblCVV, lblExpiryDate, lblAmount;
    JTextField tfCardNumber, tfCVV, tfExpiryDate, tfAmount;
    JButton btnPay, btnCancel;

    public Pay(String source, String destination) {
        setTitle("Card Payment");
        setLayout(new GridLayout(5, 2, 10, 10));
        setSize(400, 300);
        setLocation(450, 200);

        // Initialize components
        lblCardNumber = new JLabel("Card Number: ");
        lblCVV = new JLabel("CVV: ");
        lblExpiryDate = new JLabel("Expiry Date (MM/YY): ");
        lblAmount = new JLabel("Amount: ");

        tfCardNumber = new JTextField();
        tfCVV = new JTextField();
        tfExpiryDate = new JTextField();
        tfAmount = new JTextField();
        tfAmount.setEditable(false); // Make amount field read-only

        btnPay = new JButton("Pay");
        btnCancel = new JButton("Cancel");

        btnPay.addActionListener(this);
        btnCancel.addActionListener(this);

        // Add components to the layout
        add(lblCardNumber);
        add(tfCardNumber);
        add(lblCVV);
        add(tfCVV);
        add(lblExpiryDate);
        add(tfExpiryDate);
        add(lblAmount);
        add(tfAmount);
        add(btnPay);
        add(btnCancel);

        // Fetch the amount based on source and destination
        fetchAmount(source.trim(), destination.trim());

        setVisible(true);
    }

    // Fetch the amount based on source and destination
    private void fetchAmount(String source, String destination) {
        if (source == null || source.trim().isEmpty() || destination == null || destination.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Source or destination cannot be empty.");
            dispose();
            return;
        }

        try {
            Conn conn = new Conn();  // Assuming Conn is your database connection class
            String query = "SELECT amount FROM flight WHERE source = ? AND destination = ?";
            PreparedStatement pstmt = conn.c.prepareStatement(query);
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String amount = rs.getString("amount");  // Fetch the amount
                tfAmount.setText(amount);  // Display the amount in the text field
            } else {
                JOptionPane.showMessageDialog(this, "No flight found for the selected source and destination.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching flight amount.");
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnPay) {
            String cardNumber = tfCardNumber.getText();
            String cvv = tfCVV.getText();
            String expiryDate = tfExpiryDate.getText();
            String amount = tfAmount.getText();

            // Check for missing card details
            if (cardNumber.isEmpty() || cvv.isEmpty() || expiryDate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all card details.");
            } else {
                // Assuming you will link this to actual payment processing logic
                JOptionPane.showMessageDialog(this, "Payment of Rs. " + amount + " successful!");
                dispose();  // Close the payment window after successful payment
            }
        } else if (ae.getSource() == btnCancel) {
            dispose();  // Close the payment window if canceled
        }
    }
}
