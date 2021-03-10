package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        addListeners();
    }

    private void addListeners() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to close this window?", "Close Window?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    controller.logOff();
                    System.exit(0);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private void initComponents(){
        setTitle("MSN 2.0");
        setSize(new Dimension(900,600));
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

    public void createMessage(String text, ImageIcon icon, ArrayList<String> arrayList) {
        controller.createMessage(text, icon, arrayList);
    }

    public void setMessageText(String text, ImageIcon image, String user, String dateTime) {
        chatPanel.setMessageText(text, image, user, dateTime);
    }

    private class WindowListener extends WindowAdapter {
        @Override
        public void windowClosed(WindowEvent e) {
            System.out.println("Closing");
            System.exit(0);
        }
    }
}
