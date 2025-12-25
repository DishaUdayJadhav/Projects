package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ChatClientModel 
{
    private List<String> messages;
    private static final String FILE_NAME = "chat_client_history.txt";

    public ChatClientModel() 
    {
        messages = new ArrayList<>();
        loadMessagesFromFile();
    }

    public void addMessage(String sender, String message, String timestamp) 
    {
        String fullMessage = "[" + timestamp + "] " + sender + ": " + message;
        messages.add(fullMessage);
        appendMessageToFile(fullMessage);
    }

    public List<String> getMessages() 
    {
        return messages;
    }

    public void clearMessages() 
    {
        messages.clear();
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
                messages.add(line);
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
