package model;

import java.time.LocalDate;

public class StudyLog 
{
    private int id;
    private LocalDate date;
    private String subject;
    private double duration;
    private String description;

    // Constructor without ID (for new records)
    public StudyLog(LocalDate date, String subject, double duration, String description) 
    {
        this.date = date;
        this.subject = subject;
        this.duration = duration;
        this.description = description;
    }

    // Constructor with ID (for existing records from database)
    public StudyLog(int id, LocalDate date, String subject, double duration, String description) 
    {
        this.id = id;
        this.date = date;
        this.subject = subject;
        this.duration = duration;
        this.description = description;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public double getDuration() { return duration; }
    public void setDuration(double duration) { this.duration = duration; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() 
    {
        return date + " | " + subject + " | " + duration + " hrs | " + description;
    }
}