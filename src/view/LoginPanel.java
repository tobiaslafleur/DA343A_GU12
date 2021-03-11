package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Klassen LoginPanel är den panel som används för att ta logga in till systemet.
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class LoginPanel extends JPanel {

    private MainFrame mainFrame;
    private JLabel lblUsername;
    private JTextField txtUsername;
    private JLabel lblChooseIcon;
    private JLabel lblIcon;

    private JButton btnLogIn;
    private JButton btnChooseFile;

    /**
     * Initialiserar ett MainFrame-objekt och kör de metoder angivna i konstruktorn
     * @param mainFrame MainFrame
     */
    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initializeComponents();
        initializeGUI();
        registerListeners();
    }

    /**
     * Initialiserar komponenter
     */
    private void initializeComponents() {
        lblUsername = new JLabel("Username:");

        txtUsername = new JTextField();
        txtUsername.setPreferredSize(new Dimension(100, 25));

        lblChooseIcon = new JLabel("Choose icon:");

        lblIcon = new JLabel();
        lblIcon.setPreferredSize(new Dimension(200, 200));

        btnLogIn = new JButton("Login");

        btnChooseFile = new JButton("Choose Icon");

    }

    /**
     * Initialiserar GUI:t.
     */
    private void initializeGUI() {
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(900, 600));
        setMaximumSize(new Dimension(900,600));
        setMinimumSize(new Dimension(900,600));

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

    /**
     * Registerar lyssnare på knapparna.
     */
    private void registerListeners() {
        btnChooseFile.addActionListener(new BtnChooseFileListener());
        btnLogIn.addActionListener(new BtnLoginListener());
    }

    /**
     * Sätter bilden och visar upp den i GUI:t
     * @param filepath filvägen till bilden som användaren valt.
     */
    public void setImage(String filepath) {
        ImageIcon icon = new ImageIcon(filepath);

        Image image = icon.getImage();
        Image newImg = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);

        lblIcon.setIcon(icon);
        repaint();
        revalidate();
    }

    /**
     * Klassen BtnLoginListener implementerar ActionListener som sedan läggs till i registerListeners()
     */
    private class BtnLoginListener implements ActionListener {

        /**
         * Tar in användarnamnet samt vald bild och startar kedjan för att logga in.
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = txtUsername.getText();
            ImageIcon icon = (ImageIcon) lblIcon.getIcon();

            if(!username.isEmpty() && icon != null) {
                mainFrame.setUser(username, icon);
                mainFrame.updateGuiToChat();
            }
        }
    }

    /**
     * Klassen BtnFileChooser implementerar ActionListener som sedan läggs till i registerListeners()
     */
    private class BtnChooseFileListener implements ActionListener {

        /**
         * Initialiserar en ny FileChooserFrame och kallar på funktionen för att få välja användarbild.
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            FileChooserFrame fcf = new FileChooserFrame(mainFrame);
            fcf.getIcon();
        }
    }
}
