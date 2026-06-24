import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// FILE NAME MUST BE: selectedplayershow.java

public class selectedplayershow extends JFrame {

    JTable table;
    DefaultTableModel model;

    public selectedplayershow() {

        setTitle("Selected Players");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // TITLE
        JLabel title = new JLabel("SELECTED PLAYERS");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(147, 41, 3));
        title.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(title, BorderLayout.NORTH);

        // TABLE COLUMNS
        String[] columns = {
                "Player ID",
                "Player Name",
                "Team Name",
                "Game Name",
                "Age"
        };

        model = new DefaultTableModel(columns,0);

        table = new JTable(model);

        table.setRowHeight(35);

        // TABLE HEADER COLOR
        table.getTableHeader().setBackground(new Color(0,0,139));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);

        mainPanel.add(sp, BorderLayout.CENTER);

        // BACK BUTTON
        JButton back = new JButton("BACK");

        back.setBackground(new Color(0,0,139));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.WHITE);

        bottomPanel.add(back);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // LOAD DATA
        loadSelectedPlayers();

        // BACK BUTTON ACTION
        back.addActionListener(e -> {
            new dash();
            dispose();
        });

        setSize(850,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ================= LOAD SELECTED PLAYERS =================

    void loadSelectedPlayers() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM selected_players";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                model.addRow(new Object[]{

                        rs.getString("player_id"),
                        rs.getString("player_name"),
                        rs.getString("team_name"),
                        rs.getString("game_name"),
                        rs.getInt("age")
                });
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MAIN METHOD
    public static void main(String[] args) {

        new selectedplayershow();
    }
}