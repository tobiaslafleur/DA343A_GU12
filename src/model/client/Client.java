package model.client;

import model.Message;
import model.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private String ip;
    private int port;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;

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

    public Socket getSocket() {
        if(socket == null) {
            return null;
        }
        return socket;
    }

    private void startClient() {
        ClientThread clientThread = new ClientThread();
        clientThread.start();
    }

    class ClientThread extends Thread {
        @Override
        public void run() {
            while(true) {

            }
        }
    }

    public void send(String message) {
        try {
            //oos.writeObject(new Message());
            oos.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private class Listener extends Thread {
        public void run() {
            String message;
            try {
                while (true) {
                    message = ois.readUTF();
                    //controller.newMessage
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        }
    }


}
