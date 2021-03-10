package controller;

import model.Message;
import model.User;
import model.client.Client;
import view.MainFrame;

import javax.swing.*;
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
        client = new Client("localhost", 2345, user, this);
        client.login();
    }

    public void logOff() {
        if(client != null) {
            client.closeStreams();
        }
    }

    public void updateOnlineUsers(ArrayList<String> users) {
        view.updateOnlineUsers(users);
    }

    public void addContact(String selected) {
        client.setNewContact(selected);
    }

    public void updateContactList() {
        ArrayList<String> arrayList = new ArrayList<>();
        List<User> contactList = client.getUser().getContacts();

        for (User user : contactList) {
            arrayList.add(user.getUsername());
        }

        view.updateContactList(arrayList);
    }

    public void restartClient() {
        client = null;
    }

    public void createMessage(String text, ImageIcon icon, ArrayList<String> arrayList) {
        ArrayList<String> tempArrayList = new ArrayList<>();

        for(String s : arrayList) {
            if(!tempArrayList.contains(s)) {
                tempArrayList.add(s);
            }
        }

        arrayList = tempArrayList;

        for(int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).equals(client.getUser().getUsername())) {
                arrayList.remove(i);
                i = arrayList.size();
            }
        }

        if(arrayList.size() >= 1) {
            Message message = new Message(text, icon, client.getUser(), arrayList) ;

            client.sendMessage(message);
        }
    }

    public void showMessage(Message message) {
        view.setMessageText(message.getText(), message.getIcon(), message.getUser().getUsername(), message.getMessageReceivedString());
    }

}
