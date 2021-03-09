package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblChooseIcon;
    private JLabel lblIcon;

    private JButton btnLogIn;
    private JButton btnChooseFile;

    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
        initializeGUI();
        registerListeners();
    }

    private void initializeComponents() {
        lblUsername = new JLabel("Username:");

        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(100, 25));

        lblChooseIcon = new JLabel("Choose icon:");

        lblIcon = new JLabel("Icon goes here");
        lblIcon.setPreferredSize(new Dimension(200, 200));

        btnLogIn = new JButton("Login");

        btnChooseFile = new JButton("Choose Icon");

    }

    private void initializeGUI() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 800));
        setMaximumSize(new Dimension(600,800));
        setMinimumSize(new Dimension(600,800));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblChooseIcon, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(btnChooseFile, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(lblIcon, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnLogIn, gbc);
    }

    private void registerListeners() {
        btnChooseFile.addActionListener(new BtnChooseFileListener());
        btnLogIn.addActionListener(new BtnLoginListener());
    }

    public void setImage(String filepath) {
        ImageIcon icon = new ImageIcon(filepath);
        lblIcon.setIcon(icon);
        repaint();
        revalidate();
    }

    private class BtnLoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = txtUsername.getText();
            ImageIcon icon = (ImageIcon) lblIcon.getIcon();

            if(!username.isEmpty() && icon != null) {
                mainFrame.setUser(username, icon);
                mainFrame.dispose();
            }
        }
    }

    private class BtnChooseFileListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileChooserFrame fcf = new FileChooserFrame(mainFrame);
            fcf.getIcon();
        }
    }
}
