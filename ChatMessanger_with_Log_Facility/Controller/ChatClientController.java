package controller;

import model.ChatClientModel;
import view.ChatClientView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClientController 
{
    private ChatClientModel model;
    private ChatClientView view;
    private PrintWriter out;

    public ChatClientController(ChatClientModel model, ChatClientView view) 
    {
        this.model = model;
        this.view = view;

        // Load existing messages
        for (String msg : model.getMessages()) 
        {
            view.appendMessage(msg);
        }

        // Connect to server
        connectToServer();

        // Button listeners
        view.addSendButtonListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String message = view.getInputMessage().trim();
                if (!message.isEmpty()) 
                {
                    String timestamp = getCurrentTimestamp();
                    model.addMessage("Client", message, timestamp);
                    view.appendMessage("[" + timestamp + "] Client: " + message);
                    view.clearInputField();

                    if (out != null) 
                    {
                        out.println(message);
                        out.flush();
                    }
                }
            }
        });

        view.addClearButtonListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                model.clearMessages();
                view.clearChat();
            }
        });

        view.addExitButtonListener(e -> System.exit(0));
    }

    private void connectToServer() 
    {
        try 
        {
            Socket socket = new Socket("localhost", 12345);
            out = new PrintWriter(socket.getOutputStream());
            view.appendMessage("[Connected to server]");
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            view.appendMessage("Client Error: " + e.getMessage());
        }
    }

    public void showView() 
    {
        SwingUtilities.invokeLater(() -> view.setVisible(true));
    }

    private String getCurrentTimestamp() 
    {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
