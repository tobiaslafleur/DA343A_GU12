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

    private String name;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;

        try {
            socket = new Socket(ip, port);

            if (socket.isConnected()) {
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());

                Message message = new Message("This is a message", null);

                oos.writeObject(message);
                oos.flush();
            }

            startClient();
        } catch (IOException e) {
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

            }
        }
    }
}
