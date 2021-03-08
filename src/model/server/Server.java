package model.server;

import model.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private ObjectInputStream ois;;
    private ObjectOutputStream oos;
    private ReadWriteFile rwf;
    private List<User> userList;

    public static void main(String[] args) {
        Server server = new Server(2345);
        server.startServer();
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            rwf = new ReadWriteFile();
            userList = new ArrayList<>();
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
                            rwf.writeUser((User)obj);
                        } else if(obj instanceof Message) {

                        }

                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
