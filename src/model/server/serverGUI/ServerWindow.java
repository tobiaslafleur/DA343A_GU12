package model.server.serverGUI;

import controller.Controller;
import model.server.Server;

import javax.swing.*;
import java.awt.*;

public class ServerWindow extends JFrame {
    private ServerPanel serverPanel;
    private Server server;
    public ServerWindow(Server server) {
        this.server = server;
        initComponents();
        initGUI();
    }
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
