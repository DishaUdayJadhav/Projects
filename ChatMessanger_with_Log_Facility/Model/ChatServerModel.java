package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatServerModel 
{
    private List<String> messageLog;
    private final String FILE_NAME = "chat_history.txt"; 

    public ChatServerModel() 
    {
        messageLog = new ArrayList<>();
        loadMessagesFromFile(); 
    }

    public void addMessage(String sender, String message, String timestamp) 
    {
        String fullMessage = "[" + timestamp + "] " + sender + ": " + message;
        messageLog.add(fullMessage);
        appendMessageToFile(fullMessage); 
    }

    public List<String> getMessages() 
    {
        return messageLog;
    }

    public void clearMessages() 
    {
        messageLog.clear();
        clearFileContent(); 
    }

    private void appendMessageToFile(String message) 
    {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) 
        {
            writer.write(message + "\n");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private void clearFileContent() 
    {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) 
        {
            writer.print(""); 
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private void loadMessagesFromFile() 
    {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                messageLog.add(line); 
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
