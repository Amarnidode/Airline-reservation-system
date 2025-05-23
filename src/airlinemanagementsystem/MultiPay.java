
package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class MultiPay extends JFrame {

    private String pnr;
    private int totalCost;

    // UI Components for credit card payment
    private JTextField cardNumberField, expiryDateField, cvvField;
    private JButton submitButton, okButton;

    public MultiPay(String pnr, int totalCost) {
        this.pnr = pnr;
        this.totalCost = totalCost;

        setTitle("Payment");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Display PNR and Total Cost
        JLabel lblPNR = new JLabel("PNR: " + pnr);
        JLabel lblCost = new JLabel("Total Cost: ₹" + totalCost);

        // Payment method buttons
        JButton creditCardButton = new JButton("Pay with Credit Card");
        JButton payPalButton = new JButton("Pay with PayPal");

        // Credit Card Payment UI elements
        JLabel lblCardNumber = new JLabel("Card Number:");
        cardNumberField = new JTextField(16);
        JLabel lblExpiryDate = new JLabel("Expiry Date (MM/YY):");
        expiryDateField = new JTextField(5);
        JLabel lblCVV = new JLabel("CVV:");
        cvvField = new JTextField(3);
        submitButton = new JButton("Submit Payment");
        okButton = new JButton("OK"); // Add OK button

        // Hide the credit card payment details initially
        lblCardNumber.setVisible(false);
        cardNumberField.setVisible(false);
        lblExpiryDate.setVisible(false);
        expiryDateField.setVisible(false);
        lblCVV.setVisible(false);
        cvvField.setVisible(false);
        submitButton.setVisible(false);
        okButton.setVisible(false); // Initially hide OK button

        // Action listener for Pay with Credit Card button
        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show credit card payment details UI
                lblCardNumber.setVisible(true);
                cardNumberField.setVisible(true);
                lblExpiryDate.setVisible(true);
                expiryDateField.setVisible(true);
                lblCVV.setVisible(true);
                cvvField.setVisible(true);
                submitButton.setVisible(true);

                payPalButton.setEnabled(false); // Disable PayPal button
            }
        });

        // Action listener for Pay with PayPal button
        payPalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement PayPal payment logic
                JOptionPane.showMessageDialog(null, "PayPal payment successful for PNR: " + pnr);
                dispose(); // Close payment page after successful payment
            }
        });

        // Action listener for Submit Payment button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = cardNumberField.getText().trim();
                String expiryDate = expiryDateField.getText().trim();
                String cvv = cvvField.getText().trim();

                if (!isValidCard(cardNumber, expiryDate, cvv)) {
                    JOptionPane.showMessageDialog(null, "Invalid credit card details!");
                    return;
                }

                // Process the credit card payment (Simulated)
                JOptionPane.showMessageDialog(null, "Credit Card payment successful for PNR: " + pnr);

                // Show OK button after successful payment
                okButton.setVisible(true);
            }
        });

        // Action listener for OK button to open Show.java
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Show.java with the PNR
                new Show();  // Pass the PNR to the Show class
                dispose();   // Close the current window
            }
        });

        // Add components to the frame
        add(lblPNR);
        add(lblCost);
        add(creditCardButton);
        add(payPalButton);
        add(lblCardNumber);
        add(cardNumberField);
        add(lblExpiryDate);
        add(expiryDateField);
        add(lblCVV);
        add(cvvField);
        add(submitButton);
        add(okButton);

        setVisible(true);
    }

    // Method to validate the credit card details
    private boolean isValidCard(String cardNumber, String expiryDate, String cvv) {
        // Validate card number (basic check for length)
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            return false;
        }

        // Validate expiry date (MM/YY format)
        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }

        // Validate CVV (3 digits)
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        // For testing purposes
        new MultiPay("12345", 5000); // Test with a sample PNR and cost
    }
}
