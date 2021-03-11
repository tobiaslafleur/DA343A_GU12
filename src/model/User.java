package model;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * Klassen User är de användar objekt som används för att spara en användares namn och bild samt kontakter
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private ImageIcon icon;
    private List<User> contacts;

    /**
     * Konstruktorn som skapar ett User objekt
     * @param username användarens namn
     * @param icon användarens bild
     */
    public User(String username, ImageIcon icon) {
        this.username = username;
        this.icon = icon;
        contacts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public List<User> getContacts() {
        return contacts;
    }

    public void setContacts(List<User> contacts) {
        this.contacts = contacts;
    }

    public void addContact(User user) {
        contacts.add(user);
    }

    @Override
    public String toString() {
        return String.format("%s", username);
    }
}
