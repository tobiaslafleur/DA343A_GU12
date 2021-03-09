package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.logging.Logger;

public class ChatUI extends JFrame {

    private final static Logger requestLog = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final static Logger errorLog = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private JFrame newFrame = new JFrame("MSN 2.0");
    private JButton sendMessage;
    private JList connectedList;
    private JTextField inputBox;
    private JTextArea chatBox;
    private JButton logOff;
    private JLabel label;

    public ChatUI() {
        createComponents();
    }

    private void createComponents() {
        newFrame.setSize(850,550);
        newFrame.setVisible(true);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setLocationRelativeTo(null);
        newFrame.setResizable(true);
        JPanel southPanel = new JPanel();

        newFrame.add(BorderLayout.SOUTH, southPanel);
        southPanel.setBackground(Color.BLUE);
        southPanel.setLayout(new GridBagLayout());

        inputBox = new JTextField(30);
        sendMessage = new JButton("Send Message");
        logOff = new JButton("LogOff");
        chatBox = new JTextArea();
        chatBox.setEditable(false);
        connectedList = new JList<>();
        label = new JLabel();
        label.setText("Connected");

        newFrame.add(new JScrollPane(chatBox), BorderLayout.CENTER);
        newFrame.add(new JScrollPane(connectedList),BorderLayout.EAST);

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.WEST;
        GridBagConstraints right = new GridBagConstraints();
        right.anchor = GridBagConstraints.EAST;
        right.weightx = 2.0;

        chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
        sendMessage.addActionListener(new sendMessageButtonListener());
        logOff.addActionListener(new logOffListener());

        southPanel.add(inputBox, left);
        southPanel.add(sendMessage, right);
        southPanel.add(logOff,right);
        southPanel.getRootPane().setDefaultButton(sendMessage);

    }

    class sendMessageButtonListener implements ActionListener {

        private String username = "Måns";

        public void actionPerformed(ActionEvent event) {
            if (inputBox.getText().length() < 1) {
                // do nothing
            } else {
                chatBox.append("<" + username + ">:  " + inputBox.getText() + " " + " at:" + new Date() + "\n");
                inputBox.setText("");
            }
        }
    }
    private class logOffListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
