
package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener{
    
    public Home() {
        setLayout(null);
        
        // Adding background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/front.jpg"));
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, 1600, 800);
        add(image);
        
        // Heading Label
        JLabel heading = new JLabel("GROUP6 AIRLINE WELCOMES YOU");
        heading.setBounds(500, 40, 1000, 40);
        heading.setForeground(Color.BLUE);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 36));
        image.add(heading);
        
        // Menu Bar
        JMenuBar menubar = new JMenuBar();
        
        // Increase MenuBar width and Font size of MenuItems
        menubar.setPreferredSize(new Dimension(2000, 50)); // Increase the size of the menu bar
        
        // Add 'Details' Menu
        JMenu details = new JMenu("Details");
        details.setFont(new Font("Arial", Font.PLAIN, 18));  // Increased font size
        menubar.add(details);
        
        JMenuItem flightDetails = new JMenuItem("Flight Details");
        flightDetails.setFont(new Font("Arial", Font.PLAIN, 16));  // Increased font size
        flightDetails.addActionListener(this);
        details.add(flightDetails);
        
        // Add 'Book Details' Menu
        JMenu bookDetails = new JMenu("Book Details");
        bookDetails.setFont(new Font("Arial", Font.PLAIN, 18));  // Increased font size
        menubar.add(bookDetails);
        
        JMenuItem passenger = new JMenuItem("Passenger");
        passenger.setFont(new Font("Arial", Font.PLAIN, 16));  // Increased font size
        passenger.addActionListener(this);
        bookDetails.add(passenger);
        
        // Set the menu bar
        setJMenuBar(menubar);
        
        // Set frame properties
        setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize window
        setVisible(true);
    }
    
    // Handle menu item actions
    public void actionPerformed(ActionEvent ae) {
        String text = ae.getActionCommand();
        
        if (text.equals("Flight Details")) {
            new FlightInfo();  // Navigate to Flight Info page
        } else if (text.equals("Passenger")) {
            new Passenger();  // Open Passenger Details page
        } 
    }
    
    // Main method to run the program
    public static void main(String[] args) {
        new Home();
    }
}
