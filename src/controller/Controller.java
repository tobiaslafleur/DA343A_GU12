package controller;

import model.User;
import view.MainFrame;

import javax.swing.*;

public class Controller {
    private MainFrame view;

    public Controller() {
        view = new MainFrame(this);
    }

    public void setUser(String username, ImageIcon icon) {
        User user = new User(username, icon);
        System.out.println(username + " + " + icon);
    }
}
