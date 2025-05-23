package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.awt.Desktop;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.property.VerticalAlignment;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import java.sql.*;
import java.io.File;
import com.itextpdf.layout.property.TextAlignment;
import java.text.SimpleDateFormat; // Import this
import java.util.Calendar;  // Import this
import java.util.Date;  // Import this

public class BoardingPass extends JFrame implements ActionListener {

    JTextField tfpnr;
    JLabel tfname, tfnationality, lblsource, lbldestination, labelflight_name,lblflight_time, lblflight_code, lbltravel_date;
    JButton fetchButton, downloadButton;

    public BoardingPass() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel(" GROUP6 AIRLINE INDIA");
        heading.setBounds(380, 10, 450, 35);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 32));
        add(heading);

        JLabel subheading = new JLabel("Boarding Pass");
        subheading.setBounds(360, 50, 300, 30);
        subheading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        subheading.setForeground(Color.BLUE);
        add(subheading);

        JLabel lblaadhar = new JLabel("PNR DETAILS");
        lblaadhar.setBounds(60, 100, 150, 25);
        lblaadhar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblaadhar);

        tfpnr = new JTextField();
        tfpnr.setBounds(220, 100, 150, 25);
        add(tfpnr);

        fetchButton = new JButton("Enter");
        fetchButton.setBackground(Color.BLACK);
        fetchButton.setForeground(Color.WHITE);
        fetchButton.setBounds(380, 100, 120, 25);
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel lblname = new JLabel("NAME");
        lblname.setBounds(60, 140, 150, 25);
        lblname.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblname);

        tfname = new JLabel();
        tfname.setBounds(220, 140, 150, 25);
        add(tfname);

        JLabel lblnationality = new JLabel("NATIONALITY");
        lblnationality.setBounds(60, 180, 150, 25);
        lblnationality.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblnationality);

        tfnationality = new JLabel();
        tfnationality.setBounds(220, 180, 150, 25);
        add(tfnationality);

        JLabel lbladdress = new JLabel("SRC");
        lbladdress.setBounds(60, 220, 150, 25);
        lbladdress.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbladdress);

        lblsource = new JLabel();
        lblsource.setBounds(220, 220, 150, 25);
        add(lblsource);

        JLabel lblgender = new JLabel("DEST");
        lblgender.setBounds(380, 220, 150, 25);
        lblgender.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblgender);

        lbldestination = new JLabel();
        lbldestination.setBounds(540, 220, 150, 25);
        add(lbldestination);

        JLabel lblfcode = new JLabel("Flight Name");
        lblfcode.setBounds(60, 260, 150, 25);
        lblfcode.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcode);

        labelflight_name = new JLabel();
        labelflight_name.setBounds(220, 260, 150, 25);
        add(labelflight_name);

        JLabel lblfcodeLabel = new JLabel("Flight Code");
        lblfcodeLabel.setBounds(380, 260, 150, 25);
        lblfcodeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblfcodeLabel);

        lblflight_code = new JLabel();
        lblflight_code.setBounds(540, 260, 150, 25);
        add(lblflight_code);
        
        // Add this code to create a new label for flight time
        JLabel lblflight_time_label = new JLabel("Flight Time");
        lblflight_time_label.setBounds(60, 340, 150, 25);
        lblflight_time_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lblflight_time_label);

        // Create a new label to display the flight time
        lblflight_time = new JLabel();
        lblflight_time.setBounds(220, 340, 150, 25);
        add(lblflight_time);


        JLabel lbltravel_date_label = new JLabel("Date");
        lbltravel_date_label.setBounds(60, 300, 150, 25);
        lbltravel_date_label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(lbltravel_date_label);

        lbltravel_date = new JLabel();
        lbltravel_date.setBounds(220, 300, 150, 25);
        add(lbltravel_date);

        downloadButton = new JButton("Download PDF");
        downloadButton.setBackground(Color.BLACK);
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setBounds(220, 370, 150, 25);
        downloadButton.addActionListener(this);
        add(downloadButton);

        setSize(1000, 450);
        setLocation(300, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == fetchButton) {
            String pnr = tfpnr.getText();

            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM booking_details WHERE PNR = '" + pnr + "'";
                ResultSet rs = conn.s.executeQuery(query);

                if (rs.next()) {
                    tfname.setText(rs.getString("name"));
                    tfnationality.setText(rs.getString("nationality"));
                    lblsource.setText(rs.getString("source"));
                    lbldestination.setText(rs.getString("destination"));
                    labelflight_name.setText(rs.getString("flight_name"));
                    lblflight_code.setText(rs.getString("flight_code"));
                    lbltravel_date.setText(rs.getString("travel_date"));
                    String flightTime = rs.getString("flight_time");

                    System.out.println("Flight Time: " + flightTime);
                    
                    lblflight_time.setText(flightTime);

                } else {
                    JOptionPane.showMessageDialog(null, "Please enter correct PNR");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == downloadButton) {
    try {
        String pnr = tfpnr.getText();
        String name = tfname.getText();
        String nationality = tfnationality.getText();
        String source = lblsource.getText();
        String destination = lbldestination.getText();
        String flight_Name = labelflight_name.getText();
        String flight_Code = lblflight_code.getText();
        String travel_date = lbltravel_date.getText();
        String flightTime = lblflight_time.getText();

        // Parse flightTime into a Date object
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date flightTimeDate = sdf.parse(flightTime);

        // Subtract 2 hours to get the boarding time
        Calendar cal = Calendar.getInstance();
        cal.setTime(flightTimeDate);
        cal.add(Calendar.HOUR, -2); // Subtract 2 hours for boarding time
        Date boardingTimeDate = cal.getTime();

        // Format boarding time back to a string
        String boardingTime = sdf.format(boardingTimeDate);

        // Define the directory path for PDF save
        String saveDirectory = "C:\\Users\\amarn\\OneDrive\\Documents\\NetBeansProjects\\AirlineManagementSystem\\src\\airlinemanagementsystem\\BoardingPass_pdf";
        new File(saveDirectory).mkdirs();

        // PDF file path
        String filePath = saveDirectory + File.separator + "BoardingPass_" + pnr + ".pdf";
        System.out.println("Saving PDF to: " + filePath);

        // Create the PDF
        PdfWriter writer = new PdfWriter(filePath);
        com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph(" GROUP6 AIRLINE ").setBold().setFontSize(24).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));
        document.add(new Paragraph("Boarding Pass").setFontSize(18).setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));
        document.add(new Paragraph("\n"));

        // Table for boarding pass details
        Table table = new Table(new float[]{1, 3});

        // Add PNR
        table.addCell(new Cell().add(new Paragraph("PNR")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(pnr)).setTextAlignment(TextAlignment.CENTER));

        // Add Name
        table.addCell(new Cell().add(new Paragraph("Name")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(name)).setTextAlignment(TextAlignment.CENTER));

        // Add Nationality
        table.addCell(new Cell().add(new Paragraph("Nationality")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(nationality)).setTextAlignment(TextAlignment.CENTER));

        // Add Source and Destination
        table.addCell(new Cell().add(new Paragraph("Source / Destination")).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        table.addCell(new Cell().add(new Paragraph(source + " / " + destination)).setTextAlignment(TextAlignment.CENTER));

        // Add Flight Name
        table.addCell(new Cell().add(new Paragraph("Flight Name")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(flight_Name)).setTextAlignment(TextAlignment.CENTER));

        // Add Flight Code
        table.addCell(new Cell().add(new Paragraph("Flight Code")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(flight_Code)).setTextAlignment(TextAlignment.CENTER));

        // Add Travel Date
        table.addCell(new Cell().add(new Paragraph("Travel Date")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(travel_date)).setTextAlignment(TextAlignment.CENTER));

        // Add Boarding Time
        table.addCell(new Cell().add(new Paragraph("Boarding Time")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(boardingTime)).setTextAlignment(TextAlignment.CENTER));

        // Add Flight Time
        table.addCell(new Cell().add(new Paragraph("Flight Time")).setTextAlignment(TextAlignment.CENTER));
        table.addCell(new Cell().add(new Paragraph(flightTime)).setTextAlignment(TextAlignment.CENTER));

        // Add the table to the document
        Paragraph paragraph = new Paragraph();
        paragraph.add(table);
        paragraph.setTextAlignment(TextAlignment.CENTER);

        document.add(paragraph);
        document.add(new Paragraph("\nThank you for choosing group6 Airline!").setTextAlignment(TextAlignment.CENTER).setFontSize(14));
        document.close();

        // Notify success and open the PDF
        JOptionPane.showMessageDialog(null, "PDF Downloaded Successfully");
        Desktop.getDesktop().open(new File(filePath));
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error generating or opening PDF.");
    }
}

    }

    public static void main(String[] args) {
        new BoardingPass();
    }
}
