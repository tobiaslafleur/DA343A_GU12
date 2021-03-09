package view;

import javax.swing.*;
import java.awt.*;

public class FileChooserFrame extends JFrame {

    private FileChooserPanel fileChooserPanel;
    private JFileChooser fileChooser;
    private MainFrame mainFrame;

    public FileChooserFrame(MainFrame mainFrame){
        this.mainFrame = mainFrame;
        fileChooserPanel = new FileChooserPanel(this);
        //initComponents();
        //initGui();
    }

    private void initComponents() {
        setTitle("Choose Icon");
        setSize(new Dimension(600,600));
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(600, 600));
        setPreferredSize(new Dimension(600,600));
        setVisible(true);
        setResizable(false);
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        fileChooser = new JFileChooser();
    }

    private void initGui() {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(fileChooserPanel, gbc);
    }

    public void setImage(String filepath) {mainFrame.setImage(filepath);}

    public void getIcon() {
        fileChooserPanel.getFile();
    }
}
