package model.server.serverGUI;

import model.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ServerPanel extends JPanel {
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JButton btnFilter;
    private JTextArea txtResults;
    private JScrollPane scrollPane;
    private Server server;


    public ServerPanel(Server server) {
        this.server = server;
        initComponents();
        initGUI();
        registerListeners();
    }

    private void initComponents() {
        lblStartDate = new JLabel("Start Date");
        lblStartDate.setPreferredSize(new Dimension(250, 50));

        lblEndDate = new JLabel("End Date");
        lblEndDate.setPreferredSize(new Dimension(250, 50));

        txtStartDate = new JTextField();
//        txtStartDate.set
        txtStartDate.setPreferredSize(new Dimension(250, 50));

        txtEndDate = new JTextField();
        txtEndDate.setPreferredSize(new Dimension(250, 50));

        btnFilter = new JButton("Filter");
        btnFilter.setPreferredSize(new Dimension(250, 50));

        txtResults = new JTextArea();
        txtResults.setEditable(false);
        scrollPane = new JScrollPane(txtResults);
        scrollPane.setPreferredSize(new Dimension(400, 300));
    }
    private void initGUI() {
        setPreferredSize(new Dimension(580,680));
        setMaximumSize(new Dimension(580,680));
        setMinimumSize(new Dimension(580,680));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        add(lblStartDate, gbc);
        gbc.gridx = 1;
        add(lblEndDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(txtStartDate, gbc);
        gbc.gridx = 1;
        add(txtEndDate, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnFilter, gbc);

        gbc.gridy = 3;
        add(scrollPane, gbc);
    }
    private void registerListeners() {
        btnFilter.addActionListener(new FilterListener());
    }

    private class FilterListener implements ActionListener {
        private StringBuilder stringBuilder;

        @Override
        public void actionPerformed(ActionEvent e) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
           try {
               java.util.Date dateStart = format.parse(txtStartDate.getText());
               java.util.Date dateEnd = format.parse(txtEndDate.getText());
                server.getLogs(dateStart, dateEnd);
           } catch (Exception exception) {
               exception.printStackTrace();
           }


        }
    }
}
