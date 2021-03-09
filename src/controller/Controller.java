package controller;

import model.User;
import model.client.Client;
import view.MainFrame;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

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

    public void addContact(String selected) {
        client.setNewContact(selected);
    }

    public void setContact(User contact) {
        User clientUser = client.getUser();
        clientUser.addContact(contact);
    }

    public void updateContactList() {
        ArrayList<String> arrayList = new ArrayList<>();
        List<User> contactList = client.getUser().getContacts();

        for(int i = 0; i < contactList.size(); i++) {
            arrayList.add(contactList.get(i).getUsername());
        }

        view.updateContactList(arrayList);
    }

    public void restartClient() {
        client = null;
    }

    private class ClientListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

        }
    }
}
