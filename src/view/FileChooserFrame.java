package view;

import javax.swing.*;
import java.awt.*;

/**
 * Klassen FileChooserFrame är den Frame som används för att ta välja vilken bild man vill skicka till en mottagare.
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class FileChooserFrame extends JFrame {
    private FileChooserPanel fileChooserPanel;
    private JFileChooser fileChooser;
    private MainFrame mainFrame;

    /**
     * Instansierar ett MainFrame-objekt och initialiserar en FileChooserPanel och en JFileChooser
     * @param mainFrame MainFrame
     */
    public FileChooserFrame(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        fileChooserPanel = new FileChooserPanel(this);
        fileChooser = new JFileChooser();
    }

    /**
     * Sätter vilken ImageIcon som användaren valt vid inloggning.
     * @param filepath filepath till bilden som användaren valt
     */
    public void setImage(String filepath) {mainFrame.setImage(filepath);}

    /**
     * Tillkallar fileChooserPanel som sätter vilken bild användaren valt.
     */
    public void getIcon() {
        fileChooserPanel.getFile();
    }

    /**
     * Sätter vilken ImageIcon som användaren valt vid skickande av bild-meddelande.
     * @return returnerar vald bild
     */
    public ImageIcon getImage() {
        ImageIcon image;
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filepath = fileChooser.getSelectedFile().getPath();
            image = new ImageIcon(filepath);
            return image;
        }
        return null;
    }
}
