import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class matchsview {

    public static void main(String[] args) {

        new matchsview1();
    }
}

class matchsview1 extends JFrame {

    JTable table;
    DefaultTableModel model;

    matchsview1() {

        setTitle("MATCH SCHEDULE");

        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);

        // TITLE
        JLabel title = new JLabel("MATCH SCHEDULE");

        title.setFont(new Font("Arial", Font.BOLD, 24));

        title.setForeground(new Color(147, 41, 3));

        title.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(title, BorderLayout.NORTH);

        // TABLE COLUMN
        String[] cols = {

                "MATCH NO",
                "PLAYER 1",
                "TEAM 1",
                "VS",
                "PLAYER 2",
                "TEAM 2",
                "GAME"

        };

        model = new DefaultTableModel(cols,0);

        table = new JTable(model);

        table.setRowHeight(35);

        table.setFont(new Font("Arial", Font.PLAIN, 14));

        // HEADER COLOR
        table.getTableHeader().setBackground(new Color(0,0,139));

        table.getTableHeader().setForeground(Color.WHITE);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);

        mainPanel.add(sp, BorderLayout.CENTER);

        // BACK BUTTON
        JButton back = new JButton("BACK");

        back.setBackground(new Color(0,0,139));

        back.setForeground(Color.WHITE);

        back.setFocusPainted(false);

        JPanel bottom = new JPanel();

        bottom.setBackground(Color.WHITE);

        bottom.add(back);

        mainPanel.add(bottom, BorderLayout.SOUTH);

        add(mainPanel);

        // LOAD DATABASE DATA
        loadData();

        // BACK BUTTON ACTION
        back.addActionListener(e -> {

            new dash();

            dispose();

        });

        setSize(1100,500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    // ================= SHOW MATCH DATA =================

    void loadData() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM matches";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            // SHOW DATA
            while(rs.next()) {

                model.addRow(new Object[]{

                        rs.getString("match_no"),
                        rs.getString("player1"),
                        rs.getString("team1"),
                        "VS",
                        rs.getString("player2"),
                        rs.getString("team2"),
                        rs.getString("game_name")

                });

            }

            rs.close();

            pst.close();

            con.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}