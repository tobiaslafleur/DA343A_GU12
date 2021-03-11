package model.client;

import controller.Controller;
import model.Message;
import model.User;
import model.server.ServerLogger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Klassen Client sköter klient sidan av uppkopplingen mot servern och tar emot objekt via strömmar
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class Client {
    private Controller controller;
    private boolean running = true;
    private String ip;
    private int port;
    private Socket socket;
    private ServerLogger logger = new ServerLogger();
    private User user;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    /**
     * Konstruktorn för Client, skapar en ny Client och kopplar upp med Socketen samt öppnar strömmarna
     * @param ip ipAdressen som klienten skall ansluta till
     * @param port porten som klien skall asnluta på
     * @param user Vilken användare som just nu skall
     * @param controller referens till controllern
     */
    public Client(String ip, int port, User user, Controller controller) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.controller = controller;

        try {
            socket = new Socket(ip, port);

            if (socket.isConnected()) {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
            }

            startClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Skickar ett meddelande till servern så att servern också stänger sin socket, denna metod stänger klient socketen
     */
    public void closeStreams() {
        try {
            oos.writeObject("CLIENT_DISCONNECT");
            oos.flush();
            oos.close();

            running = false;

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Startar en ny Clienttråd för varje klient som ansluter
     */
    private void startClient() {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    /**
     *
     * @param message Message objekt som skall skickas till servern
     */
    public void sendMessage(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Innre klass som hanteras av tråd
     */
    class ClientThread extends Thread {
        @Override
        public void run() {
            while (running) {
                try {
                    Object obj = ois.readObject();

                    if (obj instanceof List) {
                        ArrayList<String> list = (ArrayList<String>) obj;
                        System.out.println(list);
                        controller.updateOnlineUsers(list);
                    } else if (obj instanceof User) {
                        user = (User) obj;
                        controller.updateContactList();
                    } else if (obj instanceof Message) {
                        Message message = (Message) obj;
                        message.setMessageSent(LocalDateTime.now());
                        controller.showMessage(message);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Skickar User objekt som är inloggad till servern.
     */
    public void login() {
        try {
            oos.writeObject(user);
            oos.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    /**
     * Skickar den valda användaren till servern för att lagra som kontakt
     * @param selected Den valdra anslutna klienten man vill lägga till i sin kontaktlista.
     */
    public void setNewContact(String selected) {
        try {
            oos.writeObject(selected);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @return User objekt
     */
    public User getUser() {
        return this.user;
    }
}
