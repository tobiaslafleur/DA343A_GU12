package model.client;

import controller.Controller;
import model.Message;
import model.User;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Controller controller;

    private boolean running = true;

    private String ip;
    private int port;
    private Socket socket;
    private User user;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private PropertyChangeSupport listeners = new PropertyChangeSupport(this);

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

    public void closeStreams() {
        try {
            oos.writeObject("CLIENT_DISCONNECT");
            oos.flush();
            oos.close();

            running = false;

            socket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void startClient() {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    public void sendMessage(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ClientThread extends Thread {
        @Override
        public void run() {
            while(running) {
                try {
                    Object obj = ois.readObject();

                    if(obj instanceof List) {
                        ArrayList<String> list = (ArrayList<String>) obj;
                        System.out.println(list);
                        controller.updateOnlineUsers(list);
                    } else if(obj instanceof User) {
                        user = (User) obj;
                        controller.updateContactList();
                    } else if(obj instanceof Message) {
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

    public void login() {
        try {
            oos.writeObject(user);
            oos.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    public void setNewContact(String selected) {
        try {
            oos.writeObject(selected);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser() { return this.user; }
}
