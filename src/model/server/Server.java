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
    private List<String> currentUsers;

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
            currentUsers = new ArrayList<>();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() {
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    private boolean isUserOnline(User user) {
        for(ClientHandler ch : clientHandlers) {
            if(ch.user.getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    class ServerThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    //userList = rwf.getUsers();

                    //System.out.println(userList);

                    Socket socket = serverSocket.accept();

                    if(socket.isConnected()) {
                        System.out.println("connected");
                        ois = new ObjectInputStream(socket.getInputStream());
                        oos = new ObjectOutputStream(socket.getOutputStream());

                        Object obj = ois.readObject();

                        if(obj instanceof User) {
                            if(!isUserOnline((User) obj)) {
                                clientHandlers.add(new ClientHandler(socket, (User) obj));
                                rwf.writeUser((User) obj);
                                currentUsers.add(((User) obj).getUsername());
                                oos.writeObject(currentUsers);
                                oos.flush();
                            } else {
                                System.out.println("User already online");
                            }
                        } else if(obj instanceof Message) {
                            Message message = (Message) obj;
                            message.setMessageReceived(LocalDateTime.now());
                            for(int i = 0; i < message.getReceivers().size(); i++) {
                                for(ClientHandler ch : clientHandlers) {
                                    if(ch.user.getUsername().equals(message.getReceivers().get(i).getUsername())) {
                                        ch.sendMessage(message);
                                    }
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
            //oos.flush();
        }
    }
}
