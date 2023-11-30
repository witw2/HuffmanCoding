package pl.edu.pw.ee;

import pl.edu.pw.ee.HuffCode;
import pl.edu.pw.ee.HuffDecode;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame {
    private JButton compressButton;
    private JButton decompressButton;
    private JButton selectFileButton;
    private JLabel filePathLabel;

    public GUI() {
        setTitle("Huffman Compression/Decompression");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10-pixel padding at the top
        JPanel buttonPanel = new JPanel(new FlowLayout());

        filePathLabel = new JLabel("No file selected", SwingConstants.CENTER);
        selectFileButton = new JButton("Select File");
        compressButton = new JButton("Compress");
        decompressButton = new JButton("Decompress");

        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File desktopDir = new File(System.getProperty("user.home"), "Desktop");
                JFileChooser fileChooser = new JFileChooser(desktopDir);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathLabel.setText(selectedFile.getAbsolutePath());
                }
            }
        });

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = filePathLabel.getText();
                if (filePath.equals("No file selected")) {
                    JOptionPane.showMessageDialog(null, "No file selected.");
                    return;
                }
                File file = new File(filePath);
                if (!file.exists() || !file.canRead() || !file.canWrite()) {
                    JOptionPane.showMessageDialog(null, "File does not exist or cannot be read/written.");
                    return;
                }
                try {
                    HuffCode.main(new String[]{filePath});
                    JOptionPane.showMessageDialog(null, "File compressed successfully!");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error compressing file: " + ioException.getMessage());
                }
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = filePathLabel.getText();
                if (filePath.equals("No file selected")) {
                    JOptionPane.showMessageDialog(null, "No file selected.");
                    return;
                }
                File file = new File(filePath);
                if (!file.exists() || !file.canRead() || !file.canWrite()) {
                    JOptionPane.showMessageDialog(null, "File does not exist or cannot be read/written.");
                    return;
                }
                try {
                    HuffDecode.main(new String[]{filePath});
                    JOptionPane.showMessageDialog(null, "File decompressed successfully!");
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(null, "Error decompressing file: " + ioException.getMessage());
                }
            }
        });

        mainPanel.add(selectFileButton, BorderLayout.NORTH);
        mainPanel.add(filePathLabel, BorderLayout.CENTER);

        buttonPanel.add(compressButton);
        buttonPanel.add(decompressButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}