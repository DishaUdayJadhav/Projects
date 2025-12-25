package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ChatClientView extends JFrame 
{
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton, clearButton, exitButton;

    public ChatClientView() 
    {
        setTitle("Chat Client");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();

        sendButton = new JButton("Send");
        clearButton = new JButton("Clear Chat");
        exitButton = new JButton("Exit");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));
        buttonPanel.add(sendButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputField, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);
    }

    public String getInputMessage() 
    {
        return inputField.getText();
    }

    public void clearInputField() 
    {
        inputField.setText("");
    }

    public void appendMessage(String message) 
    {
        chatArea.append(message + "\n");
    }

    public void clearChat() 
    {
        chatArea.setText("");
    }

    public void addSendButtonListener(ActionListener listener) 
    {
        sendButton.addActionListener(listener);
    }

    public void addClearButtonListener(ActionListener listener) 
    {
        clearButton.addActionListener(listener);
    }

    public void addExitButtonListener(ActionListener listener) 
    {
        exitButton.addActionListener(listener);
    }
}
