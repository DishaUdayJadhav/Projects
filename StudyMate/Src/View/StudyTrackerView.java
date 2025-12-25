package view;

import controller.StudyTrackerController;
import model.StudyLog;
import utils.CSVExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StudyTrackerView extends JFrame 
{
    private StudyTrackerController controller;
    private JTable studyTable;
    private DefaultTableModel tableModel;
    private JTextArea summaryArea;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;

    public StudyTrackerView() 
    {
        this.controller = new StudyTrackerController();
        controller.setView(this);
        
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        refreshData();
        
        setTitle("Marvellous Study Tracker - MVC Architecture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() 
    {
        // Create table model and table
        String[] columnNames = {"ID", "Date", "Subject", "Duration (hrs)", "Description"};
        tableModel = new DefaultTableModel(columnNames, 0) 
        {
            @Override
            public boolean isCellEditable(int row, int column) 
            {
                return false; // Make table read-only
            }
        };
        
        studyTable = new JTable(tableModel);
        studyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studyTable.setRowHeight(25);
        
        // Setup table columns
        studyTable.getColumnModel().getColumn(0).setMinWidth(50);
        studyTable.getColumnModel().getColumn(0).setMaxWidth(50);
        studyTable.getColumnModel().getColumn(1).setMinWidth(100);
        studyTable.getColumnModel().getColumn(1).setMaxWidth(120);
        studyTable.getColumnModel().getColumn(2).setMinWidth(120);
        studyTable.getColumnModel().getColumn(3).setMinWidth(100);
        studyTable.getColumnModel().getColumn(3).setMaxWidth(120);
        
        // Setup table sorter
        sorter = new TableRowSorter<>(tableModel);
        studyTable.setRowSorter(sorter);
        
        // Search field
        searchField = new JTextField(20);
        searchField.setToolTipText("Search by subject, description, or date...");
        
        // Summary area
        summaryArea = new JTextArea(12, 35);
        summaryArea.setEditable(false);
        summaryArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        summaryArea.setBackground(new Color(248, 248, 248));
    }

    private void setupLayout() 
    {
        setLayout(new BorderLayout());

        // Create menu bar
        createMenuBar();

        // Create toolbar
        createToolBar();

        // Create main panel with split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Left panel - Study logs table with search
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Study Logs"));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        JButton clearSearchButton = new JButton("Clear");
        clearSearchButton.addActionListener(e -> clearSearch());
        searchPanel.add(clearSearchButton);
        
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(studyTable), BorderLayout.CENTER);
        
        // Right panel - Summary
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Summary Reports"));
        rightPanel.add(new JScrollPane(summaryArea), BorderLayout.CENTER);
        
        JPanel summaryButtonPanel = new JPanel(new FlowLayout());
        JButton dateSummaryButton = new JButton("Summary by Date");
        JButton subjectSummaryButton = new JButton("Summary by Subject");
        dateSummaryButton.addActionListener(e -> showSummaryByDate());
        subjectSummaryButton.addActionListener(e -> showSummaryBySubject());
        summaryButtonPanel.add(dateSummaryButton);
        summaryButtonPanel.add(subjectSummaryButton);
        rightPanel.add(summaryButtonPanel, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        splitPane.setDividerLocation(600);
        
        add(splitPane, BorderLayout.CENTER);

        // Status bar
        JLabel statusBar = new JLabel(" Ready - Connected to MySQL Database");
        statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusBar, BorderLayout.SOUTH);
    }

    private void createMenuBar() 
    {
        JMenuBar menuBar = new JMenuBar();
        
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exportCSVItem = new JMenuItem("Export to CSV");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exportCSVItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Edit menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem addLogItem = new JMenuItem("Add Study Log");
        JMenuItem editLogItem = new JMenuItem("Edit Study Log");
        JMenuItem deleteLogItem = new JMenuItem("Delete Study Log");
        editMenu.add(addLogItem);
        editMenu.add(editLogItem);
        editMenu.add(deleteLogItem);
        
        // View menu
        JMenu viewMenu = new JMenu("View");
        JMenuItem summaryByDateItem = new JMenuItem("Summary by Date");
        JMenuItem summaryBySubjectItem = new JMenuItem("Summary by Subject");
        JMenuItem refreshItem = new JMenuItem("Refresh");
        viewMenu.add(summaryByDateItem);
        viewMenu.add(summaryBySubjectItem);
        viewMenu.addSeparator();
        viewMenu.add(refreshItem);
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);

        // Menu event handlers
        addLogItem.addActionListener(e -> addStudyLog());
        editLogItem.addActionListener(e -> editStudyLog());
        deleteLogItem.addActionListener(e -> deleteStudyLog());
        exportCSVItem.addActionListener(e -> exportToCSV());
        summaryByDateItem.addActionListener(e -> showSummaryByDate());
        summaryBySubjectItem.addActionListener(e -> showSummaryBySubject());
        refreshItem.addActionListener(e -> refreshData());
        exitItem.addActionListener(e -> System.exit(0));
    }

    private void createToolBar() 
    {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");
        JButton exportButton = new JButton("Export CSV");
        
        // Set button sizes
        Dimension buttonSize = new Dimension(80, 30);
        addButton.setPreferredSize(buttonSize);
        editButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);
        refreshButton.setPreferredSize(buttonSize);
        exportButton.setPreferredSize(buttonSize);
        
        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);
        toolBar.addSeparator();
        toolBar.add(refreshButton);
        toolBar.addSeparator();
        toolBar.add(exportButton);
        
        add(toolBar, BorderLayout.NORTH);

        // Toolbar event handlers
        addButton.addActionListener(e -> addStudyLog());
        editButton.addActionListener(e -> editStudyLog());
        deleteButton.addActionListener(e -> deleteStudyLog());
        refreshButton.addActionListener(e -> refreshData());
        exportButton.addActionListener(e -> exportToCSV());
    }

    private void setupEventHandlers() 
    {
        // Search functionality
        searchField.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyReleased(KeyEvent e) 
            {
                performSearch();
            }
        });

        // Window closing event
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
        });
    }

    private void performSearch() 
    {
        String searchTerm = searchField.getText().trim();
        List<StudyLog> searchResults = controller.searchStudyLogs(searchTerm);
        updateTable(searchResults);
    }

    private void clearSearch() 
    {
        searchField.setText("");
        refreshData();
    }

    public void refreshData() 
    {
        List<StudyLog> logs = controller.getAllStudyLogs();
        updateTable(logs);
    }

    private void updateTable(List<StudyLog> logs) 
    {
        tableModel.setRowCount(0); // Clear existing data
        
        for (StudyLog log : logs) 
        {
            Object[] row = 
            {
                log.getId(),
                log.getDate(),
                log.getSubject(),
                log.getDuration(),
                log.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    private void addStudyLog() 
    {
        StudyLogDialog dialog = new StudyLogDialog(this, "Add Study Log", null);
        dialog.setVisible(true);
        
        StudyLog newLog = dialog.getStudyLog();
        if (newLog != null) 
        {
            if (controller.addStudyLog(newLog.getSubject(), newLog.getDuration(), newLog.getDescription())) 
            {
                JOptionPane.showMessageDialog(this, "Study log added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } else 
            {
                JOptionPane.showMessageDialog(this, "Failed to add study log.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editStudyLog() 
    {
        int selectedRow = studyTable.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Please select a study log to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Convert view row to model row (important for sorted tables)
        int modelRow = studyTable.convertRowIndexToModel(selectedRow);
        
        int id = (Integer) tableModel.getValueAt(modelRow, 0);
        LocalDate date = LocalDate.parse(tableModel.getValueAt(modelRow, 1).toString());
        String subject = tableModel.getValueAt(modelRow, 2).toString();
        double duration = Double.parseDouble(tableModel.getValueAt(modelRow, 3).toString());
        String description = tableModel.getValueAt(modelRow, 4).toString();

        StudyLog existingLog = new StudyLog(id, date, subject, duration, description);
        StudyLogDialog dialog = new StudyLogDialog(this, "Edit Study Log", existingLog);
        dialog.setVisible(true);

        StudyLog updatedLog = dialog.getStudyLog();
        if (updatedLog != null) 
        {
            if (controller.updateStudyLog(updatedLog)) 
            {
                JOptionPane.showMessageDialog(this, "Study log updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Failed to update study log.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteStudyLog() 
    {
        int selectedRow = studyTable.getSelectedRow();
        if (selectedRow == -1) 
        {
            JOptionPane.showMessageDialog(this, "Please select a study log to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int result = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this study log?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) 
        {
            // Convert view row to model row
            int modelRow = studyTable.convertRowIndexToModel(selectedRow);
            int id = (Integer) tableModel.getValueAt(modelRow, 0);
            
            if (controller.deleteStudyLog(id)) 
            {
                JOptionPane.showMessageDialog(this, "Study log deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshData();
            } 
            else 
            {
                JOptionPane.showMessageDialog(this, "Failed to delete study log.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportToCSV() 
    {
        if (tableModel.getRowCount() == 0) 
        {
            JOptionPane.showMessageDialog(this, "No data to export.", "No Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<StudyLog> logs = controller.getAllStudyLogs();
        CSVExporter exporter = new CSVExporter();
        
        if (exporter.exportToCSV(logs, this)) 
        {
            JOptionPane.showMessageDialog(this, "Data exported successfully!", "Export Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showSummaryByDate() 
    {
        Map<LocalDate, Double> summary = controller.getSummaryByDate();
        StringBuilder sb = new StringBuilder();
        sb.append("SUMMARY BY DATE\n");
        sb.append("================\n\n");
        
        if (summary.isEmpty()) 
        {
            sb.append("No data available.\n");
        } 
        else 
        {
            double totalHours = 0;
            for (Map.Entry<LocalDate, Double> entry : summary.entrySet()) 
            {
                sb.append(String.format("%-12s: %.2f hours\n", entry.getKey(), entry.getValue()));
                totalHours += entry.getValue();
            }
            sb.append("\n").append("=".repeat(25)).append("\n");
            sb.append(String.format("Total Hours: %.2f\n", totalHours));
        }
        
        summaryArea.setText(sb.toString());
    }

    private void showSummaryBySubject() 
    {
        Map<String, Double> summary = controller.getSummaryBySubject();
        StringBuilder sb = new StringBuilder();
        sb.append("SUMMARY BY SUBJECT\n");
        sb.append("==================\n\n");
        
        if (summary.isEmpty()) 
        {
            sb.append("No data available.\n");
        } 
        else 
        {
            double totalHours = 0;
            for (Map.Entry<String, Double> entry : summary.entrySet()) 
            {
                sb.append(String.format("%-15s: %.2f hours\n", entry.getKey(), entry.getValue()));
                totalHours += entry.getValue();
            }
            sb.append("\n").append("=".repeat(30)).append("\n");
            sb.append(String.format("Total Hours: %.2f\n", totalHours));
        }
        
        summaryArea.setText(sb.toString());
    }
}