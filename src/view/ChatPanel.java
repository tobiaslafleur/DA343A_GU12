package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ChatPanel extends JPanel {
    private MainFrame mainFrame;

    private JTextField txtMessageBox;
    private JTextArea txtAreaChatBox;

    private JList listConnected;
    private JList listFriends;

    private JButton btnSendMsg;
    private JButton btnLogOff;

    public ChatPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
        initGui();
        registerListeners();
    }

    private void initComponents() {
        txtAreaChatBox = new JTextArea();
        txtAreaChatBox.setSize(new Dimension(550, 475));
        txtAreaChatBox.setPreferredSize(new Dimension(550, 475));
        txtAreaChatBox.setMinimumSize(new Dimension(550, 475));
        txtAreaChatBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        txtMessageBox = new JTextField();
        txtMessageBox.setSize(new Dimension(550, 25));
        txtMessageBox.setPreferredSize(new Dimension(550, 25));
        txtMessageBox.setMinimumSize(new Dimension(550, 25));

        listConnected = new JList();
        listConnected.setSize(new Dimension(175, 500));
        listConnected.setPreferredSize(new Dimension(175, 500));
        listConnected.setMinimumSize(new Dimension(175, 500));
        listConnected.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        listFriends = new JList();
        listFriends.setSize(new Dimension(175, 500));
        listFriends.setPreferredSize(new Dimension(175, 500));
        listFriends.setMinimumSize(new Dimension(175, 500));
        listFriends.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        btnSendMsg = new JButton("Send Message");
        btnSendMsg.setSize(new Dimension(550, 100));
        btnSendMsg.setPreferredSize(new Dimension(550, 100));
        btnSendMsg.setMinimumSize(new Dimension(550, 100));

        btnLogOff = new JButton("Log off");
        btnLogOff.setSize(new Dimension(350, 100));
        btnLogOff.setPreferredSize(new Dimension(350, 100));
        btnLogOff.setMinimumSize(new Dimension(350, 100));
    }

    private void initGui() {
        setLayout(new GridBagLayout());
        setSize(new Dimension(900, 600));
        setPreferredSize(new Dimension(900, 600));
        setMinimumSize(new Dimension(900, 600));
        setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(txtAreaChatBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(txtMessageBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(listConnected, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(listFriends, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(btnSendMsg, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnLogOff, gbc);
    }

    private void registerListeners() {
        btnSendMsg.addActionListener(new BtnSendMsgListener());
    }

    class BtnSendMsgListener implements ActionListener {

        private String username = "Måns";

        public void actionPerformed(ActionEvent event) {
            if (txtMessageBox.getText().length() < 1) {
                // do nothing
            } else {
                txtAreaChatBox.append("<" + username + ">:  " + txtMessageBox.getText() + " " + " at:" + new Date() + "\n");
                txtMessageBox.setText("");
            }
        }
    }

}
