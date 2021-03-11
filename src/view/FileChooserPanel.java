package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Klassen FileChooserPanel är den Panel som används för att ta välja vilken bild man vill ha som användarbild
 * @version 1.0
 * @author Tobias la Fleur, Philip Persson, Måns Olsson, Satya Singh, Alexandros Karakitsos
 */
public class FileChooserPanel extends JPanel {

    private FileChooserFrame fileChooserFrame;
    private JFileChooser fileChooser;

    /**
     * Initialiserar ett FileChooserFrame-objekt samt kör de metoder angivna i konstruktorn.
     * @param fileChooserFrame FileChooserFrame
     */
    public FileChooserPanel(FileChooserFrame fileChooserFrame){
        this.fileChooserFrame = fileChooserFrame;
        initComponents();
        initGUI();
    }

    /**
     * Initialiserar fileChooser-komponent.
     */
    private void initComponents() {
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg"));
    }

    /**
     * initialiserar GUI:t
     */
    private void initGUI(){
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 600));
        setMaximumSize(new Dimension(600,600));
        setMinimumSize(new Dimension(600,600));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(fileChooser, gbc);
    }

    /**
     * Sätter vilken filepath användaren valt och skickar till fileChooserFrame.setImage()
     */
    public void getFile(){
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            String filepath = fileChooser.getSelectedFile().getPath();
            fileChooserFrame.setImage(filepath);
        }
    }
}
