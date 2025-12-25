# Java Chat Messanger with Log Facility Application (MVC Architecture)

This Project is a Java-Based peer-to-peer Chat Application demonstrating the **Model-View-Controller (MVC)** architecture.
It enables real-time text based communication between users using JAVA SOCKET PROGRAMMING.
Along with Providing basic chat functionality,it also maintains LOG FILE that records all conversation details in a periodic fashion for future reference.
The application is platform and architecture independent,running on any system with a Java Runtime Environment(JRE).
The application includes a Server and a Client module, each with Send, Clear Chat, Exit buttons, and persistent chat history storage.

## Features

### Key Features 
- **Peer-to-Peer Communication**: Uses Java Sockets for direct Communication between clients.
- **Chat Log Maintainance**     : Messages are saved in a `.txt` file and loaded on next startup.
                                  Provides periodic logging for organized record-keeping.
- **Live Communication**        : Server and Client can exchange messages in real-time.
- **Cross-Platform**            : Runs seamlessly across different operating systems and architectures.
- **Lightweight & Extensible**  : Can be extended to support group chat,file sharing or encryption.

### Architecture
- **Model**     : Handles data storage and file operations.
- **View**      : Manages the graphical user interface.
- **Controller**: Manages user interaction, event handling, and logic.

---

## Requirements

- Java JDK 8 or higher
- Any Java IDE (e.g., IntelliJ IDEA, Eclipse) or use terminal

---

## File Structure

ChatApplication/
│
├── controller/
│ ├── ChatServerController.java
│ └── ChatClientController.java
│
├── model/
│ ├── ChatServerModel.java
│ └── ChatClientModel.java
│
├── view/
│ ├── ChatServerView.java
│ └── ChatClientView.java
│
├── ServerApp.java
├── ClientApp.java
├── server_history.txt (auto-created)
└── client_history.txt (auto-created)


---
## Learning Outcomes
- Practical knwoledge of Java Socket Programming(TCP/IP).
- Hands on experience with network programming concepts(client-server,peer-to-peer).
- Implementation of logging mechanism in applications.
- Understanding of platform-independent communication in Java.
- Foundation for building scalable chat/messaging applications.


## How to Compile and Run

### Compile

Open terminal in the project directory and run:

javac controller/*.java model/*.java view/*.java ServerApp.java ClientApp.java

Run Server
java ServerApp

Run Client (in another terminal or on another machine)

java ClientApp


*** Notes
Make sure the server is started before the client.

Both applications must be allowed by the firewall for network communication.

Messages are stored in server_history.txt and client_history.txt.

*** Author
Developed by Disha Jadhav.