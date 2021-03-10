package model.server;

import model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ReadWriteFile {
    private OutputStreamWriter osw;
    private FileOutputStream fos;
    private ObjectOutputStream oos;

    private FileInputStream fis;
    private ObjectInputStream ois;

    private String filePath = "files/userlist.txt";

    public ReadWriteFile() {

    }

    public void writeUser(User user) {
        try {
            fos = new FileOutputStream("files/users/" + user.getUsername() + ".dat");
            oos = new ObjectOutputStream(fos);

            oos.writeObject(user);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean alreadyExistsInList(String user) {
        try {
            fis = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;

            while((line = br.readLine()) != null) {
                if (line.equals(user)) {
                    return true;
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addToTextFile(String user) {
        try {
            File file = new File(filePath);
            FileWriter fw = new FileWriter(file, true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(user);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User readFromFile(String username) {
        try {
            fis = new FileInputStream("files/users/" + username + ".dat");
            ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            System.out.println(obj);
            return (User) obj;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
