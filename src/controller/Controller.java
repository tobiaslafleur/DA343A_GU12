package controller;

import model.User;
import model.client.Client;
import view.MainFrame;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
        client.addPropertyChangeListener(new ClientListener());
        client.login();
    }

    public void logOff() {
        client.closeStreams();
    }

    public void updateOnlineUsers(ArrayList<String> users) {
        view.updateOnlineUsers(users);
    }

    private class ClientListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

        }
    }
}
