package model;

import javax.swing.*;

public class Message {

    private String text;
    private ImageIcon icon;
    private User user;

    public Message(String text, ImageIcon icon, User user) {
        this.text = text;
        this.icon = icon;
        this.user = user;
    }

    public Message(String text) {
        this.text = text;
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
}
