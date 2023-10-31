import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI {
    private static JLabel originalImageLabel = new JLabel("Original Image");
    private static JLabel newImageLabel = new JLabel("New Image");
    public static void main(String args[]) {
        // create JFrame
        JFrame frame = new JFrame("Pixels to Edges: Exploring Advanced Techniques in Edge Detection");
        frame.setSize(1800, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create a Panel for the Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());

        // add dropdown for selecting operator
        JLabel operatorLabel = new JLabel("Operator: ");
        String[] operators = {"Roberts", "Sobel", "Prewitt", "Canny"};
        JComboBox<String> operatorDropdown = new JComboBox<>(operators);
        headerPanel.add(operatorLabel);
        headerPanel.add(operatorDropdown);

        // add File selector
        JLabel fileLabel = new JLabel("File: ");
        JFileChooser fileChooser = new JFileChooser();
        headerPanel.add(fileLabel);
        headerPanel.add(fileChooser);

        // add Apply button
        JButton applyButton = new JButton("Apply");
        headerPanel.add(applyButton);

        // add Panels for Original and New Images
        JPanel imagesPanel = new JPanel();
        imagesPanel.setLayout(new FlowLayout());

        imagesPanel.add(originalImageLabel);
        imagesPanel.add(newImageLabel);

        // add headerPanel and imagesPanel to Frame
        frame.setLayout(new BorderLayout());
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(imagesPanel, BorderLayout.CENTER);

        // apply button action
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get selected algorithm
                String algorithm = (String) operatorDropdown.getSelectedItem();
                // get selected file
                File file = fileChooser.getSelectedFile();
                if(file != null) {
                    executeAlgorithm(algorithm, file.getPath());
                }
            }
        });

        fileChooser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                    File selectedFile = fileChooser.getSelectedFile();
                    loadImage(selectedFile, originalImageLabel);
                }
            }
        });

        // Display Frame
        frame.setVisible(true);
    }

    private static void executeAlgorithm(String algorithm, String path) {
    	//Call main method
    	String [] mainArgs = {"--"+algorithm.toLowerCase(), path};
    	Main.main(mainArgs);
       
    }

    public static void loadImage(File file, JLabel imageLabel) {
        try {
            BufferedImage img = ImageIO.read(file);
            ImageIcon icon = new ImageIcon(img);
            imageLabel.setIcon(icon);
            imageLabel.setText("");
        } catch (IOException e) {
            imageLabel.setText("Error loading image.");
            e.printStackTrace();
        }
    }

}
