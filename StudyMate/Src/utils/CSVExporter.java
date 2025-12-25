package utils;

import model.StudyLog;
import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter 
{
    
    public boolean exportToCSV(List<StudyLog> logs, JFrame parent) 
    {
        if (logs == null || logs.isEmpty()) 
        {
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new java.io.File("MarvellousStudy.csv"));
        fileChooser.setDialogTitle("Export Study Logs to CSV");
        
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) 
        {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) 
            {
                // Write CSV header
                writer.write("Date,Subject,Duration,Description\n");
                
                // Write data rows
                for (StudyLog log : logs) 
                {
                    writer.write(String.format("%s,%s,%.2f,%s\n",
                        log.getDate(),
                        escapeCSV(log.getSubject()),
                        log.getDuration(),
                        escapeCSV(log.getDescription())
                    ));
                }
                
                return true;
            } 
            catch (IOException e) 
            {
                JOptionPane.showMessageDialog(parent, 
                    "Failed to export data: " + e.getMessage(), 
                    "Export Error", 
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return false;
    }
    
    private String escapeCSV(String value) 
    {
        if (value == null) 
        {
            return "";
        }
        
        // If the value contains comma, newline, or double quote, wrap it in quotes
        if (value.contains(",") || value.contains("\n") || value.contains("\"")) 
        {
            // Escape any existing double quotes by doubling them
            value = value.replace("\"", "\"\"");
            return "\"" + value + "\"";
        }
        
        return value;
    }
}