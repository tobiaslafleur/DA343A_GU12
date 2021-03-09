package model.server;

import model.*;
import model.client.Client;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class Server extends Thread{
    private ServerSocket serverSocket;
    private ObjectInputStream ois;;
    private ObjectOutputStream oos;
    private ReadWriteFile rwf;
    private List<User> userList;
    private List<ClientHandler> clientHandlers;
    private ArrayList<String> currentUsers;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            rwf = new ReadWriteFile();
            userList = new ArrayList<>();
            clientHandlers = new ArrayList<>();
            currentUsers = new ArrayList<>();
            start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ClientHandler clientHandler = null;

        while(true) {
            try {
                Socket socket = serverSocket.accept();

                if(socket != null) {
                    clientHandler = new ClientHandler(socket, this);
                    clientHandlers.add(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateOnlineUsers() {
        for(ClientHandler ch : clientHandlers) {
            ch.sendOnlineList(currentUsers);
        }
    }

    /*class ServerThread {

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
                                //rwf.writeUser((User) obj);
                                currentUsers.add(((User) obj).getUsername());
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

                        for(ClientHandler ch : clientHandlers) {
                            System.out.println(currentUsers);
                            ch.sendOnlineList(currentUsers);
                        }
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    class ClientHandler extends Thread{
        private Socket socket;
        private Server server;
        private User user;

        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;

            try {
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while(true) {
                try {
                    Object obj = ois.readObject();

                    if(obj instanceof User) {
                        this.user = (User) obj;
                        currentUsers.add(user.getUsername());
                    } else if(obj instanceof Message) {
                        Message message = (Message) obj;
                        message.setMessageReceived(LocalDateTime.now());
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                server.updateOnlineUsers();
            }
        }

        public void sendMessage(Message message) {
            //oos.flush();
        }

        public void sendOnlineList(ArrayList<String> list) {
            try {
                oos.writeObject(list);
                oos.flush();
                oos.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
