package model.server;

import model.User;

import java.io.*;
import java.util.*;

public class ReadWriteFile {
    private FileOutputStream fos;
    private ObjectOutputStream oos;

    private FileInputStream fis;
    private ObjectInputStream ois;

    private List<User> userList;

    public ReadWriteFile() {
        userList = new ArrayList<>();
        readFromFile();
    }

    public void writeUser(User user) {
        try {
            userList.add(user);

            fos = new FileOutputStream("files/userlist.dat");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(userList);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getUsers() {
        return userList;
    }

    public void readFromFile() {
        try {
            fis = new FileInputStream("files/userlist.dat");
            ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();

            if(obj instanceof List) {
                userList = (List) obj;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
