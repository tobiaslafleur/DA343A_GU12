package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class ChatPanel extends JPanel {

    private MainFrame mainFrame;

    public ChatPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
        initGui();
        registerListeners();
    }

    private void initGui() {
        setSize(new Dimension(900, 600));
        setPreferredSize(new Dimension(900, 600));

    }

    private void initComponents() {

    }

    private void registerListeners() {

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

}
