package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    private Controller controller;
    private LoginPanel loginPanel;
    private ChatPanel chatPanel;

    public MainFrame(Controller controller){
        this.controller = controller;
        this.loginPanel = new LoginPanel(this);
        this.chatPanel = new ChatPanel(this);

        initComponents();
        initGui();
    }

    private void initComponents(){
        setTitle("MSN 2.0");
        setSize(new Dimension(900,600));
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900,600));
        setVisible(true);
        setResizable(false);
        setLayout(new GridBagLayout());
    }

    private void initGui(){
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginPanel,gbc);

        pack();
        setLocation(new Point(500, 100));
    }

    public void updateGuiToChat() {
        remove(loginPanel);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(chatPanel, gbc);

        pack();
        repaint();
        revalidate();
    }

    public void updateGuiToLogin() {
        remove(chatPanel);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(loginPanel, gbc);

        pack();
        repaint();
        revalidate();

        controller.restartClient();
    }

    public void setImage(String filepath) {loginPanel.setImage(filepath);}

    public void setUser(String username, ImageIcon icon) {
        controller.setUser(username, icon);
    }

    public void logOff() {
        controller.logOff();
    }

    public void updateOnlineUsers(ArrayList<String> users) {
        chatPanel.updateOnlineUsers(users);
    }

    public void addContact(String selected) {
        controller.addContact(selected);
    }

    public void updateContactList(ArrayList<String> arrayList) {
        chatPanel.updateContactList(arrayList);
    }
}
