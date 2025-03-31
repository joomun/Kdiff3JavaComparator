package gui;

import diff.FileComparator;
import diff.ReportGenerator;
import ftp.FTPDownloader;
import config.ConfigLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class KDiffGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("KDiff-like Comparator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout());

            JButton compareButton = new JButton("Start Comparison");
            compareButton.addActionListener(e -> performComparison());

            frame.add(compareButton, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    private static void performComparison() {
        try {
            ConfigLoader configLoader = new ConfigLoader("config/config.properties");
            FileComparator fileComparator = new FileComparator();
            ReportGenerator reportGenerator = new ReportGenerator();
            FTPDownloader ftpDownloader = new FTPDownloader();

            Map<Integer, String[]> filePairs = configLoader.getFilePairs();
            for (Map.Entry<Integer, String[]> entry : filePairs.entrySet()) {
                String sourceFile = entry.getValue()[0];
                String targetFile = entry.getValue()[1];

                // Download files if needed
                ftpDownloader.downloadFile(configLoader.getProperty("source.server"), configLoader.getProperty("source.user"), configLoader.getProperty("source.pass"), sourceFile, "local_" + sourceFile);
                ftpDownloader.downloadFile(configLoader.getProperty("target.server"), configLoader.getProperty("target.user"), configLoader.getProperty("target.pass"), targetFile, "local_" + targetFile);

                // Compare files
                List<String> differences = fileComparator.compareFiles("local_" + sourceFile, "local_" + targetFile);

                // Generate the report
                reportGenerator.generateReport("reports/comparison_report.txt", differences);

                JOptionPane.showMessageDialog(null, "Comparison complete! Report generated.");
            }
        } catch (IOException | JSchException | SftpException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred: " + ex.getMessage());
        }
    }
}
