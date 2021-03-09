package model;

import javax.swing.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.List;

public class Message implements Serializable {

    private String text;
    private ImageIcon icon;
    private User user;
    private List<User> receivers;

    private LocalDateTime messageReceived;
    private LocalDateTime messageSent;

    public Message(String text, ImageIcon icon, User user, ArrayList<User> receivers) {
        this.text = text;
        this.icon = icon;
        this.user = user;
        this.receivers = receivers;
    }

    public Message(String text, User user, ArrayList<User> receivers) {
        this.text = text;
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

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
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
