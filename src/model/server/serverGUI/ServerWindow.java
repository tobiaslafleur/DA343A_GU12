package model.server.serverGUI;

import model.server.Server;

import javax.swing.*;
import java.awt.*;

/**
 * klassen ServerWindow är huvudfönstret för server GUI:t och innehåller ServerPanelen
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */

public class ServerWindow extends JFrame {
    private ServerPanel serverPanel;
    private Server server;

    /**
     * konstruktorn instansierar Server objektet och anropar metoderna för att starta GUI:t
     */
    public ServerWindow(Server server) {
        this.server = server;
        initComponents();
        initGUI();
    }

    /**
     * metod som initierar GUI:t och instansierar serverPanel
     */
    private void initComponents() {
        setTitle("ServerGUI");
        setPreferredSize(new Dimension(600,700));
        setMaximumSize(new Dimension(600,700));
        setMinimumSize(new Dimension(600,700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        serverPanel = new ServerPanel(server);
    }

    /**
     * metoden bestämmer layout på Server GUI:t och lägger till serverPanelen
     * i ServerWindow
     */
    private void initGUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(serverPanel, gbc);
        pack();
    }
}
