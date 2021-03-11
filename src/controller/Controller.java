package controller;

import model.Message;
import model.User;
import model.client.Client;
import view.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Klassen Controller sköter kommunikation mellan view-klasserna och model-klasserna
 * @author Philip Persson, Måns Olsson, Tobias la Fleur, Alexandros Karakitsos, Satya Signh
 * @version 1.0
 */
public class Controller {
    private MainFrame view;
    private Client client;

    /**
     * konstruktor som instansierar ett MainFrame objekt
     */
    public Controller() {
        view = new MainFrame(this);
    }

    /**
     * metoden skapar en användare
     * @param username
     * @param icon
     */
    public void setUser(String username, ImageIcon icon) {
        User user = new User(username, icon);
        client = new Client("83.249.103.28", 2345, user, this);
        client.login();
    }

    /**
     * metoden används för att logga ut ur applikationen
     */
    public void logOff() {
        if(client != null) {
            client.closeStreams();
        }
    }

    /**
     * metoden uppdaterar uppkopplade användare med en lista av användare som är uppkopplade
     * @param users är listan på användare som är uppkopplade till servern
     */
    public void updateOnlineUsers(ArrayList<String> users) {
        view.updateOnlineUsers(users);
    }

    /**
     * metoden används skicka en användare man vill lägga till i sin kontaktlista till servern
     * @param selected är namnet på användaren man vill lägga till
     */
    public void addContact(String selected) {
        client.setNewContact(selected);
    }

    /**
     * metoden används för att uppdatera sin kontaktlista i GUI:t
     */
    public void updateContactList() {
        ArrayList<String> arrayList = new ArrayList<>();
        List<User> contactList = client.getUser().getContacts();

        for (User user : contactList) {
            arrayList.add(user.getUsername());
        }

        view.updateContactList(arrayList);
    }

    /**
     * metoden används för att starta om klienten
     */
    public void restartClient() {
        client = null;
    }

    /**
     * metoden används för att skapa ett meddelande som ska skickas
     * @param text är texten som ska skickas i ett meddelande
     * @param icon är bilden som ska skickas i ett meddelande
     * @param arrayList
     */
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

    /**
     * metoden används för att visa ett meddelande i klientens GUI
     * @param message är meddelandet som ska visas
     */
    public void showMessage(Message message) {
        view.setMessageText(message.getText(), message.getIcon(), message.getUser().getUsername(), message.getMessageReceivedString());
    }

}
