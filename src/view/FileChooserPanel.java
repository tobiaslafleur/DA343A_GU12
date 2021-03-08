package view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileChooserPanel extends JPanel {

    private FileChooserFrame fileChooserFrame;
    private JFileChooser fileChooser;

    public FileChooserPanel(FileChooserFrame fileChooserFrame){
        this.fileChooserFrame = fileChooserFrame;
        initComponents();
        initGUI();
    }

    private void initComponents() {
        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg"));
    }

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

    public void getFile(){
        int choice = fileChooser.showOpenDialog(this);

        if(choice == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIcon icon = new ImageIcon(file.getName());
            fileChooserFrame.setIcon(icon);
            fileChooserFrame.dispose();
        }
    }
}
