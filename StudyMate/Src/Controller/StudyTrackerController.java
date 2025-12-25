package controller;

import model.StudyLog;
import database.StudyLogDAO;
import view.StudyTrackerView;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class StudyTrackerController 
{
    private StudyLogDAO studyLogDAO;
    private StudyTrackerView view;

    public StudyTrackerController() 
    {
        this.studyLogDAO = new StudyLogDAO();
    }

    public void setView(StudyTrackerView view) 
    {
        this.view = view;
    }

    public boolean addStudyLog(String subject, double duration, String description) 
    {
        if (subject == null || subject.trim().isEmpty()) 
        {
            return false;
        }
        if (duration <= 0) 
        {
            return false;
        }
        
        StudyLog log = new StudyLog(LocalDate.now(), subject.trim(), duration, description.trim());
        return studyLogDAO.insertStudyLog(log);
    }

    public List<StudyLog> getAllStudyLogs() 
    {
        return studyLogDAO.getAllStudyLogs();
    }

    public List<StudyLog> searchStudyLogs(String searchTerm) 
    {
        if (searchTerm == null || searchTerm.trim().isEmpty()) 
        {
            return getAllStudyLogs();
        }
        return studyLogDAO.searchStudyLogs(searchTerm.trim());
    }

    public boolean updateStudyLog(StudyLog log) 
    {
        if (log == null || log.getSubject() == null || log.getSubject().trim().isEmpty()) 
        {
            return false;
        }
        if (log.getDuration() <= 0) 
        {
            return false;
        }
        return studyLogDAO.updateStudyLog(log);
    }

    public boolean deleteStudyLog(int id) 
    {
        return studyLogDAO.deleteStudyLog(id);
    }

    public Map<LocalDate, Double> getSummaryByDate() 
    {
        return studyLogDAO.getSummaryByDate();
    }

    public Map<String, Double> getSummaryBySubject() 
    {
        return studyLogDAO.getSummaryBySubject();
    }

    public void refreshView() 
    {
        if (view != null) 
        {
            view.refreshData();
        }
    }
}