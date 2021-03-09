package controller;

import model.User;
import model.client.Client;
import view.ChatUI;
import view.MainFrame;

import javax.swing.*;

public class Controller {
    private MainFrame view;
    private Client client;

    public Controller() {
        view = new MainFrame(this);
    }

    public void setUser(String username, ImageIcon icon) {
        new User(username, icon);
        //client = new Client("83.249.103.28", 2345);
        view.updateGui();
    }
}
