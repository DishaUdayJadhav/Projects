package controller;

import model.ChatServerModel;
import view.ChatServerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatServerController 
{
    private ChatServerModel model;
    private ChatServerView view;
    private ServerSocket serverSocket;

    public ChatServerController(ChatServerModel model, ChatServerView view) 
    {
        this.model = model;
        this.view = view;

        view.addSendButtonListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String message = view.getInputMessage();
                if (!message.trim().isEmpty()) 
                {
                    String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    model.addMessage("Server", message, timestamp);
                    view.appendMessage("[" + timestamp + "] Server: " + message);
                    view.clearInputField();
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

        view.addExitButtonListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0);
            }
        });

        // Load existing messages if any
        List<String> messages = model.getMessages();
        for (String msg : messages) 
        {
            view.appendMessage(msg);
        }

        // Start server thread
        startServer();
    }

    private void startServer() 
    {
        new Thread(() -> 
        {
            try 
            {
                serverSocket = new ServerSocket(12345);
                view.appendMessage("[Server started on port 12345. Waiting for client...]");

                Socket clientSocket = serverSocket.accept();
                view.appendMessage("[Client connected]");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String message;
                while ((message = in.readLine()) != null) {
                    String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
                    model.addMessage("Client", message, timestamp);
                    view.appendMessage("[" + timestamp + "] Client: " + message);
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
                view.appendMessage("Server Error: " + e.getMessage());
            }
        }).start();
    }
}
