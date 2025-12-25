package view;

import model.StudyLog;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class StudyLogDialog extends JDialog 
{
    private JTextField subjectField;
    private JTextField durationField;
    private JTextArea descriptionArea;
    private StudyLog studyLog;
    private boolean confirmed;

    public StudyLogDialog(Frame parent, String title, StudyLog log) 
    {
        super(parent, title, true);
        this.studyLog = log;
        this.confirmed = false;
        
        initializeComponents();
        layoutComponents();
        setupEventHandlers();
        
        if (log != null) 
        {
            populateFields();
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() 
    {
        subjectField = new JTextField(20);
        durationField = new JTextField(20);
        descriptionArea = new JTextArea(6, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
    }

    private void layoutComponents() 
    {
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Subject
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Subject:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(subjectField, gbc);
        
        // Duration
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Duration (hours):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        formPanel.add(durationField, gbc);
        
        // Description
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; 
        gbc.weighty = 1.0;
        formPanel.add(new JScrollPane(descriptionArea), gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.setPreferredSize(new Dimension(80, 30));
        cancelButton.setPreferredSize(new Dimension(80, 30));
        
        saveButton.addActionListener(e -> saveAction());
        cancelButton.addActionListener(e -> dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() 
    {
        // Add input validation for duration field
        durationField.addKeyListener(new java.awt.event.KeyAdapter() 
        {
            public void keyTyped(java.awt.event.KeyEvent evt) 
            {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != '.' && c != '\b') 
                {
                    evt.consume();
                }
            }
        });
    }

    private void populateFields() 
    {
        subjectField.setText(studyLog.getSubject());
        durationField.setText(String.valueOf(studyLog.getDuration()));
        descriptionArea.setText(studyLog.getDescription());
    }

    private void saveAction() 
    {
        if (validateInput()) 
        {
            String subject = subjectField.getText().trim();
            double duration = Double.parseDouble(durationField.getText().trim());
            String description = descriptionArea.getText().trim();
            
            if (studyLog == null) 
            {
                // Creating new log
                studyLog = new StudyLog(LocalDate.now(), subject, duration, description);
            } 
            else 
            {
                // Updating existing log
                studyLog.setSubject(subject);
                studyLog.setDuration(duration);
                studyLog.setDescription(description);
            }
            
            confirmed = true;
            dispose();
        }
    }

    private boolean validateInput() 
    {
        if (subjectField.getText().trim().isEmpty()) 
        {
            JOptionPane.showMessageDialog(this, "Please enter a subject.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            subjectField.requestFocus();
            return false;
        }
        
        try 
        {
            double duration = Double.parseDouble(durationField.getText().trim());
            if (duration <= 0) 
            {
                JOptionPane.showMessageDialog(this, "Duration must be greater than 0.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                durationField.requestFocus();
                return false;
            }
        } 
        catch (NumberFormatException e) 
        {
            JOptionPane.showMessageDialog(this, "Please enter a valid duration.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            durationField.requestFocus();
            return false;
        }
        
        return true;
    }

    public StudyLog getStudyLog() 
    {
        return confirmed ? studyLog : null;
    }
}