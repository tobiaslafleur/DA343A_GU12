package model;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable {

    private String text;
    private ImageIcon icon;
    private User user;
    private String dateTime;

    private LocalDateTime ldt;

    public Message(String text, ImageIcon icon, User user) {
        this.text = text;
        this.icon = icon;
        this.user = user;
        formatDateAndTime();
    }

    public Message(String text, User user) {
        this.text = text;
        this.user = user;
        formatDateAndTime();
    }

    public void formatDateAndTime() {
        ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        dateTime = ldt.format(formatter);
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
