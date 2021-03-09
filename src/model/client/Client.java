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
import java.util.ArrayList;
import java.util.List;

public class Client {
    private Controller controller;

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
            oos.close();
            ois.close();
            socket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void startClient() {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    class ClientThread extends Thread {
        @Override
        public void run() {
            while(true) {
                try {
                    Object obj = ois.readObject();

                    if(obj instanceof List) {
                        ArrayList<String> list = (ArrayList<String>) obj;
                        System.out.println(list);
                        controller.updateOnlineUsers(list);
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
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        listeners.addPropertyChangeListener(propertyChangeListener);
    }
}
