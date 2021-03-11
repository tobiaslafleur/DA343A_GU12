package model;

import javax.swing.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;

/**
 * Klassen Message är de meddelanden som skickas på servern
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class Message implements Serializable {

    private String text;
    private ImageIcon icon;
    private User user;
    private ArrayList<String> receivers;

    private LocalDateTime messageReceived;
    private LocalDateTime messageSent;

    /**
     * Denna konstruktorn används när ett meddelande har både text och en bild
     * @param text Texten som skrivs
     * @param icon Bilden som skickas med
     * @param user Användaren som skickar
     * @param receivers En lista med de mottagare som ska ta emot meddelandet
     */
    public Message(String text, ImageIcon icon, User user, ArrayList<String> receivers) {
        this.text = text;
        this.icon = icon;
        this.user = user;
        this.receivers = receivers;
    }

    /**
     * Denna konstruktorn används när ett meddelande har bara text
     * @param text Texten som skrivs
     * @param user Användaren som skickar
     * @param receivers En lista med de mottagare som ska ta emot meddelandet
     */
    public Message(String text, User user, ArrayList<String> receivers) {
        this.text = text;
        this.user = user;
        this.receivers = receivers;
    }

    /**
     * Denna konstruktorn används när ett meddelande har bara en bild
     * @param icon Bilden som skickas med
     * @param user Användaren som skickar
     * @param receivers En lista med de mottagare som ska ta emot meddelandet
     */
    public Message(ImageIcon icon, User user, ArrayList<String> receivers) {
        this.icon = icon;
        this.user = user;
        this.receivers = receivers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(ArrayList<String> receivers) {
        this.receivers = receivers;
    }

    public LocalDateTime getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(LocalDateTime messageReceived) {
        this.messageReceived = messageReceived;
    }

    public LocalDateTime getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(LocalDateTime messageSent) {
        this.messageSent = messageSent;
    }

    public String getMessageReceivedString() {
        messageReceived = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return messageReceived.format(formatter);
    }

    public String getMessageSentString() {
        messageSent = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return messageSent.format(formatter);
    }
}
