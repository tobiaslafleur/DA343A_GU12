package model.server;

import model.*;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private ObjectInputStream ois;;
    private ObjectOutputStream oos;
    private ReadWriteFile rwf;
    private List<User> userList;
    private List<ClientHandler> clientHandlers;

    public static void main(String[] args) {
        Server server = new Server(2345);
        server.startServer();
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            rwf = new ReadWriteFile();
            userList = new ArrayList<>();
            clientHandlers = new ArrayList<>();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() {
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    class ServerThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    userList = rwf.getUsers();

                    System.out.println(userList);

                    Socket socket = serverSocket.accept();

                    if(socket.isConnected()) {
                        System.out.println("connected");
                        ois = new ObjectInputStream(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());

                        Object obj = ois.readObject();

                        if(obj instanceof User) {
                            clientHandlers.add(new ClientHandler(socket, (User) obj));
                            rwf.writeUser((User) obj);
                        } else if(obj instanceof Message) {
                            for(ClientHandler ch : clientHandlers) {
                                if(ch.socket == socket) {
                                    

                                    Message message = (Message) obj;
                                    message.setMessageReceived(LocalDateTime.now());
                                    ch.sendMessage((Message) obj);
                                }
                            }
                        }

                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ClientHandler {
        private Socket socket;
        private User user;

        public ClientHandler(Socket socket, User user) {
            this.socket = socket;
            this.user = user;
        }

        public void sendMessage(Message message) {

        }
    }
}
