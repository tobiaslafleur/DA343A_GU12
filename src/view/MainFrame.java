package view;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Klassen MainFrame är den Frame som används för att kommunicera med Controller samt har alla paneler inuti sig.
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class MainFrame extends JFrame {

    private Controller controller;
    private LoginPanel loginPanel;
    private ChatPanel chatPanel;

    /**
     * Initialiserar ett controller-objekt, instansierar en ny LoginPanel och ChatPanel samt kör de metoder som är angivna.
     * @param controller Controller
     */
    public MainFrame(Controller controller){
        this.controller = controller;
        this.loginPanel = new LoginPanel(this);
        this.chatPanel = new ChatPanel(this);

        initComponents();
        initGui();
        addListeners();
    }

    /**
     * lägger till lyssnare för default close operation. Om användaren trycker på krysset i övre högra hörnet av programmet
     * så stängs först kopplingen innan programmet avslutas.
     */
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

    /**
     * Initialiserar alla nödvändiga komponenter.
     */
    private void initComponents(){
        setTitle("MSN 2.0");
        setSize(new Dimension(900,600));
        setPreferredSize(new Dimension(900,600));
        setVisible(true);
        setResizable(false);
        setLayout(new GridBagLayout());
    }

    /**
     * Initialiserar GUI:ts ursprungliga utseende.
     */
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

    /**
     * Uppdaterar framen till ChatPanel.
     */
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

    /**
     * Uppdaterar framen till LoginPanel
     */
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

    /**
     * Medlare för FileChooserPanel och LoginPanel. Sätter vilken bild som visas i ChatPanel.
     * @param filepath filväg till vald bild
     */
    public void setImage(String filepath) {loginPanel.setImage(filepath);}

    /**
     * Medlare för LoginPanel och Controller. Sätter vilken användare som ska skickas till servern.
     * @param username valt användarnamn
     * @param icon vald användarbild
     */
    public void setUser(String username, ImageIcon icon) {
        controller.setUser(username, icon);
    }

    /**
     * Medlare mellan ChatPanel och Controller. Stänger ner kopplingen.
     */
    public void logOff() {
        controller.logOff();
    }

    /**
     * Medlare mellan Controller och ChatPanel. Uppdaterar listan på uppkopplade användare.
     * @param users uppkopplade användare
     */
    public void updateOnlineUsers(ArrayList<String> users) {
        chatPanel.updateOnlineUsers(users);
    }

    /**
     * Medlare mellan ChatPanel och Controller. Skickas för att lägga till kontakter i User-objekt.
     * @param selected vald användare
     */
    public void addContact(String selected) {
        controller.addContact(selected);
    }

    /**
     * Medlare mellan Controller och ChatPanel. Uppdaterar kontaktlistan.
     * @param arrayList användarens kontakter
     */
    public void updateContactList(ArrayList<String> arrayList) {
        chatPanel.updateContactList(arrayList);
    }

    /**
     * Medlare mellan ChatPanel och Controller. Skapar ett Meddelande-objekt.
     * @param text vald text
     * @param icon vald bild
     * @param arrayList valda mottagare
     */
    public void createMessage(String text, ImageIcon icon, ArrayList<String> arrayList) {
        controller.createMessage(text, icon, arrayList);
    }

    /**
     * Medlare mellan Controller och ChatPanel. Skriver ut mottaget meddelande.
     * @param text mottagen text
     * @param image mottagen bild
     * @param user skickad från denna användare
     * @param dateTime när meddelandet mottogs av klienten.
     */
    public void setMessageText(String text, ImageIcon image, String user, String dateTime) {
        chatPanel.setMessageText(text, image, user, dateTime);
    }
}
