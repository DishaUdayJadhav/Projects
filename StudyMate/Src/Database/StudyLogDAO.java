package database;

import model.StudyLog;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudyLogDAO 
{
    private DatabaseConnection dbConnection;

    public StudyLogDAO() 
    {
        this.dbConnection = DatabaseConnection.getInstance();
    }

    public boolean insertStudyLog(StudyLog log) 
    {
        String sql = "INSERT INTO study_logs (date, subject, duration, description) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql)) 
        {
            pstmt.setDate(1, Date.valueOf(log.getDate()));
            pstmt.setString(2, log.getSubject());
            pstmt.setDouble(3, log.getDuration());
            pstmt.setString(4, log.getDescription());
            
            return pstmt.executeUpdate() > 0;
        } 
        catch (SQLException e) 
        {
            System.err.println("Error inserting study log: " + e.getMessage());
            return false;
        }
    }

    public List<StudyLog> getAllStudyLogs() 
    {
        List<StudyLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM study_logs ORDER BY date DESC";
        
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
             {
            
            while (rs.next()) 
            {
                StudyLog log = new StudyLog(
                    rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("subject"),
                    rs.getDouble("duration"),
                    rs.getString("description")
                );
                logs.add(log);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error retrieving study logs: " + e.getMessage());
        }
        
        return logs;
    }

    public List<StudyLog> searchStudyLogs(String searchTerm) 
    {
        List<StudyLog> logs = new ArrayList<>();
        String sql = """
            SELECT * FROM study_logs 
            WHERE subject LIKE ? OR description LIKE ? OR date LIKE ?
            ORDER BY date DESC
            """;
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql)) 
        {
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) 
            {
                StudyLog log = new StudyLog(
                    rs.getInt("id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getString("subject"),
                    rs.getDouble("duration"),
                    rs.getString("description")
                );
                logs.add(log);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error searching study logs: " + e.getMessage());
        }
        
        return logs;
    }

    public boolean updateStudyLog(StudyLog log) 
    {
        String sql = "UPDATE study_logs SET subject = ?, duration = ?, description = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql)) 
        {
            pstmt.setString(1, log.getSubject());
            pstmt.setDouble(2, log.getDuration());
            pstmt.setString(3, log.getDescription());
            pstmt.setInt(4, log.getId());
            
            return pstmt.executeUpdate() > 0;
        } 
        catch (SQLException e) 
        {
            System.err.println("Error updating study log: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteStudyLog(int id) 
    {
        String sql = "DELETE FROM study_logs WHERE id = ?";
        
        try (PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(sql)) 
        {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } 
        catch (SQLException e) 
        {
            System.err.println("Error deleting study log: " + e.getMessage());
            return false;
        }
    }

    public Map<LocalDate, Double> getSummaryByDate() 
    {
        Map<LocalDate, Double> summary = new TreeMap<>();
        String sql = "SELECT date, SUM(duration) as total_duration FROM study_logs GROUP BY date ORDER BY date";
        
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
             {
            
            while (rs.next()) 
            {
                LocalDate date = rs.getDate("date").toLocalDate();
                double totalDuration = rs.getDouble("total_duration");
                summary.put(date, totalDuration);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error getting summary by date: " + e.getMessage());
        }
        
        return summary;
    }

    public Map<String, Double> getSummaryBySubject() 
    {
        Map<String, Double> summary = new TreeMap<>();
        String sql = "SELECT subject, SUM(duration) as total_duration FROM study_logs GROUP BY subject ORDER BY subject";
        
        try (Statement stmt = dbConnection.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) 
             {
            
            while (rs.next()) 
            {
                String subject = rs.getString("subject");
                double totalDuration = rs.getDouble("total_duration");
                summary.put(subject, totalDuration);
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error getting summary by subject: " + e.getMessage());
        }
        
        return summary;
    }
}