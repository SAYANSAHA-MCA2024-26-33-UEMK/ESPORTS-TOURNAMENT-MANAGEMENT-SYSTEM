import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class matchscheduler {
    public static void main(String[] args) {

        new matchscheduler1();

    }
}

class matchscheduler1 extends JFrame {

    JTable table;
    DefaultTableModel model;

    matchscheduler1() {

        setTitle("MATCH SCHEDULE");

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.WHITE);

        // TITLE
        JLabel title = new JLabel("MATCH SCHEDULE");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(147, 41, 3));
        title.setHorizontalAlignment(JLabel.CENTER);

        main.add(title, BorderLayout.NORTH);

        // TABLE
        String[] cols = {
                "Match No",
                "Player 1",
                "Team 1",
                "VS",
                "Player 2",
                "Team 2",
                "Game"
        };

        model = new DefaultTableModel(cols,0);

        table = new JTable(model);

        table.setRowHeight(35);

        table.getTableHeader().setBackground(
                new Color(0,0,139)
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        JScrollPane sp = new JScrollPane(table);

        main.add(sp, BorderLayout.CENTER);

        // BUTTONS
        JButton load = new JButton("Generate Match");
        JButton back = new JButton("Back");

        JButton[] btns = {load, back};

        for(JButton b : btns){

            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);

        }

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);

        bottom.add(load);
        bottom.add(back);

        main.add(bottom, BorderLayout.SOUTH);

        add(main);

        // GENERATE MATCH
        load.addActionListener(e -> {

            generateMatches();

        });

        // BACK
        back.addActionListener(e -> {

            new dash();
            dispose();

        });

        setSize(1000,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ================= MATCH GENERATOR =================

    void generateMatches() {

        model.setRowCount(0);

        try {

            Connection con =
                    DBConnection.getConnection();

            // ONLY SELECTED PLAYERS
            String sql =
                    "SELECT * FROM selected_players";

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            ArrayList<String[]> players =
                    new ArrayList<>();

            while(rs.next()) {

                String[] data = {

                        rs.getString("player_id"),
                        rs.getString("player_name"),
                        rs.getString("team_name"),
                        rs.getString("game_name"),
                        rs.getString("age")

                };

                players.add(data);
            }

            // MATCH CREATION
            int matchNo = 1;

            for(int i=0; i<players.size()-1; i=i+2) {

                String[] p1 = players.get(i);
                String[] p2 = players.get(i+1);

                model.addRow(new Object[] {

                        "MATCH-" + matchNo,
                        p1[1],
                        p1[2],
                        "VS",
                        p2[1],
                        p2[2],
                        p1[3]

                });

                // STORE MATCH IN DATABASE
                String insert =
                        "INSERT INTO matches(match_no,player1,team1,player2,team2,game_name) VALUES(?,?,?,?,?,?)";

                PreparedStatement pst2 =
                        con.prepareStatement(insert);

                pst2.setString(1,
                        "MATCH-" + matchNo);

                pst2.setString(2, p1[1]);
                pst2.setString(3, p1[2]);

                pst2.setString(4, p2[1]);
                pst2.setString(5, p2[2]);

                pst2.setString(6, p1[3]);

                pst2.executeUpdate();

                pst2.close();

                matchNo++;
            }

            // ODD PLAYER CHECK
            if(players.size() % 2 != 0) {

                String[] last =
                        players.get(players.size()-1);

                JOptionPane.showMessageDialog(
                        this,
                        last[1] + " gets automatic bye!"
                );
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}