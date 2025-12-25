import view.StudyTrackerView;
import javax.swing.*;

public class StudyTrackerApp 
{
    public static void main(String[] args) 
    {
        // Set system look and feel
        try 
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } 
        catch (Exception e) 
        {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }

        // Create and show the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> 
        {
            try 
            {
                new StudyTrackerView().setVisible(true);
            } 
            catch (Exception e) 
            {
                JOptionPane.showMessageDialog(null, 
                    "Failed to start application: " + e.getMessage() + 
                    "\n\nPlease ensure:\n" +
                    "1. XAMPP is running\n" +
                    "2. MySQL service is started\n" +
                    "3. MySQL JDBC driver is in classpath", 
                    "Application Error", 
                    JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}