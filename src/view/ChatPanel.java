package view;

import model.User;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Klassen ChatPanel är den panel som används för att ta emot och skicka chatt-meddelanden.
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class ChatPanel extends JPanel {
    private MainFrame mainFrame;

    private JTextField txtMessageBox;
    private JTextPane txtPaneChatBox;

    private StyledDocument doc;

    private JScrollPane scrollPane;

    private JList listConnected;
    private JList listFriends;

    private DefaultListModel<String> dlmOnlineUsers;
    private DefaultListModel<String> dlmContactList;

    private ImageIcon image;

    private JButton btnSendMsg;
    private JButton btnLogOff;
    private JButton btnAddContact;
    private JButton btnChooseImage;

    /**
     * Instansierar ett MainFrame-objekt och kör de metoder angivina i konstruktorn.
     * @param mainFrame MainFrame
     */
    public ChatPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        initComponents();
        initGui();
        registerListeners();
    }

    /**
     * Initialiserar alla GUI-objekt.
     */
    private void initComponents() {
        txtPaneChatBox = new JTextPane();
        doc = txtPaneChatBox.getStyledDocument();

        scrollPane = new JScrollPane(txtPaneChatBox);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(new Dimension(550, 475));
        scrollPane.setPreferredSize(new Dimension(550, 475));
        scrollPane.setMinimumSize(new Dimension(550, 475));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        txtMessageBox = new JTextField();
        txtMessageBox.setSize(new Dimension(550, 25));
        txtMessageBox.setPreferredSize(new Dimension(550, 25));
        txtMessageBox.setMinimumSize(new Dimension(550, 25));


        dlmOnlineUsers = new DefaultListModel<>();

        listConnected = new JList(dlmOnlineUsers);
        listConnected.setSize(new Dimension(175, 500));
        listConnected.setPreferredSize(new Dimension(175, 500));
        listConnected.setMinimumSize(new Dimension(175, 500));
        listConnected.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        dlmContactList = new DefaultListModel<>();

        listFriends = new JList(dlmContactList);
        listFriends.setSize(new Dimension(175, 500));
        listFriends.setPreferredSize(new Dimension(175, 500));
        listFriends.setMinimumSize(new Dimension(175, 500));
        listFriends.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        btnSendMsg = new JButton("Send Message");
        btnSendMsg.setSize(new Dimension(275, 100));
        btnSendMsg.setPreferredSize(new Dimension(275, 100));
        btnSendMsg.setMinimumSize(new Dimension(275, 100));

        btnChooseImage = new JButton("Choose Image");
        btnChooseImage.setSize(new Dimension(275, 100));
        btnChooseImage.setPreferredSize(new Dimension(275, 100));
        btnChooseImage.setMinimumSize(new Dimension(275, 100));

        btnAddContact = new JButton("Add Contact");
        btnAddContact.setSize(new Dimension(175, 100));
        btnAddContact.setPreferredSize(new Dimension(175, 100));
        btnAddContact.setMinimumSize(new Dimension(175, 100));

        btnLogOff = new JButton("Log off");
        btnLogOff.setSize(new Dimension(175, 100));
        btnLogOff.setPreferredSize(new Dimension(175, 100));
        btnLogOff.setMinimumSize(new Dimension(175, 100));
    }

    /**
     * Initialiserar och sätter upp GUI:t
     */
    private void initGui() {
        setLayout(new GridBagLayout());
        setSize(new Dimension(900, 600));
        setPreferredSize(new Dimension(900, 600));
        setMinimumSize(new Dimension(900, 600));
        setBackground(Color.DARK_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(txtMessageBox, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        add(listConnected, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        add(listFriends, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        add(btnSendMsg, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(btnChooseImage, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(btnAddContact, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(btnLogOff, gbc);
    }

    /**
     * Registerar lyssnare till alla knappar.
     */
    private void registerListeners() {
        btnSendMsg.addActionListener(new BtnSendMsgListener());
        btnLogOff.addActionListener(new BtnLogOffListener());
        btnAddContact.addActionListener(new BtnAddContact());
        btnChooseImage.addActionListener(new BtnChooseImage());
    }

    /**
     * Uppdaterar de användare som loggar in och loggar ut från servern.
     * @param users alla användare som är online
     */
    public void updateOnlineUsers(ArrayList<String> users) {
        dlmOnlineUsers.clear();
        dlmOnlineUsers.addAll(users);
    }

    /**
     * Uppdaterar de användare som läggs till i kontaktlistan.
     * @param arrayList alla kontakter användaren har
     */
    public void updateContactList(ArrayList<String> arrayList) {
        dlmContactList.clear();
        dlmContactList.addAll(arrayList);
    }

    /**
     * Visar upp det inkomna meddelandet.
     * @param text inkomna textmeddelandet
     * @param messageImage inkomna bilden
     * @param user inkomna användaren som skickat meddelandet
     * @param dateTime tiden då meddelandet mottogs av klienten
     */
    public void setMessageText(String text, ImageIcon messageImage, String user, String dateTime) {
        try {
            ImageIcon imageIcon = null;
            SimpleAttributeSet icon = null;

            String message = String.format("%s - %s: ", dateTime,user);

            SimpleAttributeSet right = new SimpleAttributeSet();
            StyleConstants.setBold(right, true);

            if(messageImage != null) {
                Image image = messageImage.getImage();
                Image newImg = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(newImg);

                icon = new SimpleAttributeSet();
                StyleConstants.setIcon(icon, imageIcon);
            }

            if(text != null && messageImage != null) {
                doc.insertString(doc.getLength(), message, right);
                doc.insertString(doc.getLength(), text + "\n", null);
                doc.insertString(doc.getLength(), "image \n", icon);
            } else if(text != null) {
                doc.insertString(doc.getLength(), message, right);
                doc.insertString(doc.getLength(), text + "\n", null);
            } else {
                doc.insertString(doc.getLength(), user + ": ", right);
                doc.insertString(doc.getLength(), "image \n", icon);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * BtnSendMsgListener implementerar ActionListener och läggs sedan till i registerListeners()
     */
    class BtnSendMsgListener implements ActionListener {

        /**
         * Skickar med textmeddelandet man skrivit och bilden man har valt. Om text eller bild inte är angett så skickas de som null.
         * @param event event
         */
        @Override
        public void actionPerformed(ActionEvent event) {
            String text = txtMessageBox.getText();
            ArrayList<String> arrayList = new ArrayList<>();

            if(!listConnected.getSelectedValuesList().isEmpty()) {
                arrayList = (ArrayList<String>) listConnected.getSelectedValuesList();
            }

            if(!listFriends.getSelectedValuesList().isEmpty()) {
                ArrayList<String> arrayListContacts = (ArrayList<String>) listFriends.getSelectedValuesList();
                arrayList.addAll(arrayListContacts);
            }

            if(arrayList != null) {
                mainFrame.createMessage(text, image, arrayList);
                image = null;
            }

        }
    }

    /**
     * BtnLogOffListener implementerar ActionListener och läggs sedan till i registerListeners()
     */
    class BtnLogOffListener implements ActionListener {

        /**
         * Kallar på metoder i mainFrame som stänger ner anslutning till server och uppdaterar GUI:t till LoginPanel
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            mainFrame.logOff();
            mainFrame.updateGuiToLogin();
        }
    }

    /**
     * BtnAddContact implementerar ActionListener och läggs sedan till i registerListeners()
     */
    class BtnAddContact implements ActionListener {

        /**
         * Tar in vilken sträng som är vald från listConnected och skickas till mainFrame för att läggas till som kontakt i User-objektet.
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            String selected = String.valueOf(listConnected.getSelectedValue());
            mainFrame.addContact(selected);
        }
    }

    /**
     * BtnChooseImage implementerar ActionListener och läggs sedan till i registerListeners()
     */
    class BtnChooseImage implements ActionListener {

        /**
         * Väljer vilken bild som ska sparas till image från en JFileChooser.
         * @param e event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            FileChooserFrame fcf = new FileChooserFrame(mainFrame);
            image = fcf.getImage();
        }
    }
}
