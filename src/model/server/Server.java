package model.server;

import model.*;
import model.server.serverGUI.ServerWindow;

import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Klassen Server är server sidan av projektet
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
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

    /**
     * Konstruktorn skapar serverSocketen som gör att klienterna kan koppla upp mot den, den instansierar även många av våra instansvariablar
     * @param port Porten som servern lyssnar på
     */
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

    /**
     * Server trådens run() metod, accepterar uppkopplingar mot servern och skapar nya ClientHandler objekt om uppkopplingen är OK
     */
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

    /**
     * Uppdaterar listan av onlineUsers genom att skicka ut en ny lista till alla användare
     */
    public void updateOnlineUsers() {
        for (ClientHandler ch : clientHandlers) {
            ch.sendOnlineList(currentUsers);
        }
    }

    /**
     * Returnerar en String array mellan de två inmatade tidpunkterna
     * @param to Tiden till
     * @param from Tiden ifrån
     * @return returnerar en String array
     */
    public static String[] getLog(LocalDateTime to, LocalDateTime from) {
        return ServerLogger.getLog(to, from);
    }

    /**
     * Traverserar igenom och finner User objektet med hjälp av en användares namn
     * @param contact En string av den inkommande kontaktens namn
     * @return returnerar en användare
     */
    public User findContact(String contact) {
        for (ClientHandler ch : clientHandlers) {
            if (ch.user.getUsername().equals(contact)) {
                return ch.user;
            }
        }
        return null;
    }

    /**
     * Kollar om användaren redan finns i listan och returnerar då antingen true eller false
     * @param user användarens namn
     * @return returnerar true eller false
     */
    private boolean alreadyExistsInList(String user) {
        if (rwf.alreadyExistsInList(user)) {
            return true;
        }
        return false;
    }

    /**
     * Tar bort en ClientHandler instans
     * @param clientHandler ClientHandler instansen som ska tas bort
     */
    public void removeHandler(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
    }

    /**
     * Tar bort användares från listan av användare som är anslutna
     * @param user Användaren som ska tas bort
     */
    public void removeFromOnlineList(User user) {
        currentUsers.remove(user.getUsername());
    }

    /**
     * Traverserar igenom listan av mottagare som ska få meddelandena och skickar sedan ut de med rätt ClientHandler instans till användarna, är inte mottagaren
     * uppkopplad läggs meddelandet till i en HashMap i UnsentMessage instansen
     * @param message Meddelandet som ska skickas
     */
    public void sendMessage(Message message) {
        ArrayList<String> tempList = new ArrayList<>();

        for (int i = 0; i < message.getReceivers().size(); i++) {
            for (ClientHandler ch : clientHandlers) {
                if (message.getUser().getUsername().equals(ch.user.getUsername())) {
                    ch.sendMessage(message);
                }
                if (message.getReceivers().get(i).equals(ch.user.getUsername())) {
                    ch.sendMessage(message);
                    tempList.add(message.getReceivers().get(i));
                }
            }
        }

        for(int i = 0; i < message.getReceivers().size(); i++) {
            for (String s : tempList) {
                if (s.equals(message.getReceivers().get(i))) {
                    message.getReceivers().remove(s);
                }
            }
        }

        if(message.getReceivers().size() > 0) {
            for(int i = 0; i < message.getReceivers().size(); i++) {
                unsentMessage.put(message.getReceivers().get(i), message);
            }
        }
    }

    /**
     * Kollar om där finns några meddelanden sparade i HashMapen i UnsentMessage för användaren som kopplar upp, finns där meddelanden skickas de ut
     * @param user Användaren som kopplar upp sigs namn
     */
    public void checkUnsentMessages(String user) {
        ArrayList<Message> messages = unsentMessage.get(user);

        if(messages != null) {
            for (ClientHandler ch : clientHandlers) {
                if (user.equals(ch.user.getUsername())) {
                    for (Message m : messages) {
                        ch.sendMessage(m);
                    }
                }
            }
        }
    }

    /**
     * ClientHandler är varje Klients uppkoppling mot servern som körs på vars en tråd
     */
    class ClientHandler extends Thread {
        private Socket socket;
        private Server server;
        private User user;
        private boolean running = true;

        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        /**
         * Konstruktorn för ClientHandler, öppnar strömmarna för både input och output
         * @param socket socketen som klienten är uppkopplad med
         * @param server Ett objekt av servern för att komma åt datan och metoder som finns där
         */
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

        /**
         * Trådens run() metod, tar in object och beroende på vilket sorts objekt som kommer in via strömmen utförs en rad olika metoder
         */
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
                        logger.log("Server received a message from " + user.getUsername());
                        Message message = (Message) obj;
                        message.setMessageReceived(LocalDateTime.now());
                        server.sendMessage(message);
                    } else if (obj instanceof String) {
                        String string = (String) obj;
                        if (string.equals("CLIENT_DISCONNECT")) {
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

        /**
         * Skickar meddelandena via strömmen
         * @param message Meddelandet som skickas
         */
        public synchronized void sendMessage(Message message) {
            try {
                oos.writeObject(message);
                oos.flush();
                logger.log("Message sent to " + user.getUsername());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Skickar ut listan av användare som är online via strömmen
         * @param list Listan som skickas
         */
        public synchronized void sendOnlineList(ArrayList<String> list) {
            try {
                oos.writeObject(list);
                oos.flush();
                oos.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Stänger socketen när klienten avbryter uppkopplingen
         */
        public synchronized void stopConnection() {
            try {
                logger.log(user.getUsername() + " disconnected");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Klassen UnsentMessage håller reda på meddelanden som inte skickats för att mottagaren är offline
     */
    private class UnsentMessage {

        private HashMap<String, ArrayList<Message>> unsent = new HashMap<>();

        /**
         * Stoppar in meddelanden i HashMapen med användarens namn som nyckel
         * @param user Användarens namn
         * @param message Meddelandet som inte kunde skickas
         */
        public synchronized void put(String user, Message message) {
            ArrayList<Message> unsentMessageList = unsent.get(user);

            if (unsentMessageList == null) {
                unsentMessageList = new ArrayList<>();
                unsent.put(user, unsentMessageList);
            }

            unsentMessageList.add(message);
        }

        /**
         * Returnerar en lista av alla meddelande som den användare som kommer som invärde har
         * @param user användarens namn
         * @return returnerar en ArrayList
         */
        public synchronized ArrayList<Message> get (String user) {
            return unsent.get(user);
        }
    }
}
