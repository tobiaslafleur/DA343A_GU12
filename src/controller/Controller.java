package controller;

import model.User;
import model.client.Client;
import view.ChatUI;
import view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Controller {
    private MainFrame view;
    private Client client;

    public Controller() {
        view = new MainFrame(this);
    }

    public void setUser(String username, ImageIcon icon) {
        User user = new User(username, icon);
        client = new Client("83.249.103.28", 2345, user, this);
        client.login();
    }

    public void logOff() {
        client.closeStreams();
    }

    public void updateOnlineUsers(ArrayList<String> users) {
        view.updateOnlineUsers(users);
    }
}
