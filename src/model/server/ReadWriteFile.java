package model.server;

import model.User;

import java.io.*;

/**
 * Klassen ReadWriteFile används för att läsa och skriva till filer
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class ReadWriteFile {
    private OutputStreamWriter osw;
    private FileOutputStream fos;
    private ObjectOutputStream oos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private final String filePath = "files/userlist.txt";


    /**
     * Skapar en fil om den inte redan finns och skriver sen in User objektet i filen
     * @param user User objektet
     */
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


    /**
     * Kollar om användaren redan finns och har en sparad fil, detta sparas isånnafall i userlist.txt och det är den filen som körs igenom här för att kolla om användaren redan finns
     * @param user Användar stringen
     * @return Returnerar false eller true
     */
    public boolean alreadyExistsInList(String user) {
        try {
            fis = new FileInputStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;

            while ((line = br.readLine()) != null) {
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

    /**
     * Skriver in en användares namn i userlist.txt filen
     * @param user Användar stringen
     */
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

    /**
     * Läsert ett User objekt från filen med namnet som kommer som invärde
     * @param username användarens namn
     * @return Returnerar användar objektet som finns i filen
     */
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
