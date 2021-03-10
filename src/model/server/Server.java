package model.server;

import model.*;
import model.server.serverGUI.ServerWindow;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private ReadWriteFile rwf;
    private ArrayList<String> userList;
    private List<ClientHandler> clientHandlers;
    private ArrayList<String> currentUsers;
    private ServerLogger logger;
    private UnsentMessage unsentMessage;

    public Server(int port) {
        try {
            new ServerWindow(this);
            logger = new ServerLogger();
            serverSocket = new ServerSocket(port);
            rwf = new ReadWriteFile();
            unsentMessage = new UnsentMessage();
            userList = new ArrayList<>();
            clientHandlers = new ArrayList<>();
            currentUsers = new ArrayList<>();
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ClientHandler clientHandler = null;

        while (true) {
            try {
                Socket socket = serverSocket.accept();

                if (socket != null) {
                    clientHandler = new ClientHandler(socket, this);
                    clientHandlers.add(clientHandler);
                    clientHandler.start();
                    logger.log("A new client connected to server");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateOnlineUsers() {
        for (ClientHandler ch : clientHandlers) {
            ch.sendOnlineList(currentUsers);
        }
    }


    public static String[] getLog(LocalDateTime to, LocalDateTime from) {
        return ServerLogger.getLog(to, from);
    }

    public User findContact(String contact) {
        for (ClientHandler ch : clientHandlers) {
            if (ch.user.getUsername().equals(contact)) {
                return ch.user;
            }
        }
        return null;
    }

    private boolean alreadyExistsInList(String user) {
        if (rwf.alreadyExistsInList(user)) {
            return true;
        }
        return false;
    }

    public void removeHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    public void removeFromOnlineList(User user) {
        currentUsers.remove(user.getUsername());
    }

    public void sendMessage(Message message) {
        for (int i = 0; i < message.getReceivers().size(); i++) {
            for (ClientHandler ch : clientHandlers) {
                if (message.getUser().getUsername().equals(ch.user.getUsername())) {
                    ch.sendMessage(message);
                }
                if (message.getReceivers().get(i).equals(ch.user.getUsername())) {
                    ch.sendMessage(message);
                    message.getReceivers().remove(message.getReceivers().get(i));
                }
            }
        }

        System.out.println(message.getReceivers().size());
        if(message.getReceivers().size() > 0) {
            for(int i = 0; i < message.getReceivers().size(); i++) {
                unsentMessage.put(message.getReceivers().get(i), message);
            }
        }
    }

    public void checkUnsentMessages(String user) {
        ArrayList<Message> messages = unsentMessage.get(user);

        if(messages != null) {
            System.out.println("entered NULL if statement");
            for (ClientHandler ch : clientHandlers) {
                if (user.equals(ch.user.getUsername())) {
                    for (Message m : messages) {
                        ch.sendMessage(m);
                    }
                }
            }
        }
    }

    class ClientHandler extends Thread {
        private Socket socket;
        private Server server;
        private User user;
        private boolean running = true;

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
            while (running) {
                try {
                    Object obj = ois.readObject();

                    if (obj instanceof User) {
                        User tempUser = (User) obj;
                        if (!alreadyExistsInList(tempUser.getUsername())) {
                            user = tempUser;
                            rwf.writeUser(user);
                            rwf.addToTextFile(user.getUsername());
                        } else {
                            user = rwf.readFromFile(tempUser.getUsername());
                            oos.writeObject(user);
                            oos.flush();
                        }
                        currentUsers.add(user.getUsername());
                        server.updateOnlineUsers();
                        server.checkUnsentMessages(user.getUsername());
                    } else if (obj instanceof Message) {
                        logger.log("Server recived a message from" + user.getUsername());
                        Message message = (Message) obj;
                        message.setMessageReceived(LocalDateTime.now());
                        server.sendMessage(message);
                    } else if (obj instanceof String) {
                        String string = (String) obj;
                        if (string.equals("CLIENT_DISCONNECT")) {
                            System.out.println("client disconnecting");
                            server.removeFromOnlineList(user);
                            server.updateOnlineUsers();
                            stopConnection();
                            running = false;
                            server.removeHandler(this);
                        } else {
                            User contact = server.findContact((String) obj);
                            user.addContact(contact);
                            rwf.writeUser(user);
                            oos.writeObject(user);
                            oos.flush();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        public synchronized void sendMessage(Message message) {
            try {
                oos.writeObject(message);
                oos.flush();
                logger.log("Message sent to " + user.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public synchronized void sendOnlineList(ArrayList<String> list) {
            try {
                oos.writeObject(list);
                oos.flush();
                oos.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public synchronized void stopConnection() {
            try {
                logger.log(user.getUsername() + " disconnected");
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class UnsentMessage {

        private HashMap<String, ArrayList<Message>> unsent = new HashMap<>();

        public synchronized void put(String user, Message message) {
            ArrayList<Message> unsentMessageList = unsent.get(user);

            if (unsentMessageList == null) {
                unsentMessageList = new ArrayList<>();
                unsent.put(user, unsentMessageList);
                System.out.println("inside");
            }

            System.out.println("outside");
            unsentMessageList.add(message);
        }

        public synchronized ArrayList<Message> get (String user) {
            return unsent.get(user);
        }
    }
}
