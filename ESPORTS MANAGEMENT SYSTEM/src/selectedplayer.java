import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class selectedplayer extends JFrame {

    JTable table;
    DefaultTableModel model;

    public selectedplayer() {

        setTitle("Player Selection");

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // TITLE
        JLabel title = new JLabel("PLAYER SELECTION");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(147, 41, 3));
        title.setHorizontalAlignment(JLabel.CENTER);

        mainPanel.add(title, BorderLayout.NORTH);

        // TABLE COLUMN
        String[] cols = {
                "Player ID",
                "Player Name",
                "Team Name",
                "Game Name",
                "Age",
                "Action"
        };

        model = new DefaultTableModel(cols,0);

        table = new JTable(model);

        table.setRowHeight(35);

        table.getTableHeader().setBackground(new Color(0,0,139));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);

        mainPanel.add(sp, BorderLayout.CENTER);

        // BACK BUTTON
        JButton back = new JButton("BACK");

        back.setBackground(new Color(0,0,139));
        back.setForeground(Color.WHITE);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.WHITE);

        bottom.add(back);

        mainPanel.add(bottom, BorderLayout.SOUTH);

        add(mainPanel);

        // LOAD PLAYER DATA
        loadPlayerData();

        // BACK
        back.addActionListener(e -> {
            new dash();
            dispose();
        });

        setSize(950,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ================= LOAD PLAYER =================

    void loadPlayerData() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM player";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while(rs.next()) {

                model.addRow(new Object[]{

                        rs.getString("player_id"),
                        rs.getString("player_name"),
                        rs.getString("team_name"),
                        rs.getString("game_name"),
                        rs.getInt("age"),
                        "ACTION"
                });
            }

            rs.close();
            pst.close();
            con.close();

            // BUTTON COLUMN
            table.getColumn("Action")
                    .setCellRenderer(new ButtonRenderer());

            table.getColumn("Action")
                    .setCellEditor(new ButtonEditor(new JCheckBox()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= BUTTON RENDER =================

    class ButtonRenderer extends JPanel implements TableCellRenderer {

        JButton select = new JButton("SELECT");
        JButton reject = new JButton("REJECT");

        ButtonRenderer() {

            setLayout(new FlowLayout());

            select.setBackground(new Color(0,0,139));
            select.setForeground(Color.WHITE);

            reject.setBackground(Color.RED);
            reject.setForeground(Color.WHITE);

            add(select);
            add(reject);
        }

        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {

            return this;
        }
    }

    // ================= BUTTON ACTION =================

    class ButtonEditor extends DefaultCellEditor {

        JPanel panel = new JPanel();

        JButton select = new JButton("SELECT");
        JButton reject = new JButton("REJECT");

        String playerId;
        String playerName;
        String teamName;
        String gameName;
        int age;

        public ButtonEditor(JCheckBox checkBox) {

            super(checkBox);

            panel.setLayout(new FlowLayout());

            select.setBackground(new Color(0,0,139));
            select.setForeground(Color.WHITE);

            reject.setBackground(Color.RED);
            reject.setForeground(Color.WHITE);

            panel.add(select);
            panel.add(reject);

            // SELECT BUTTON
            select.addActionListener(e -> {
                saveSelectedPlayer();
            });

            // REJECT BUTTON
            reject.addActionListener(e -> {
                saveRejectedPlayer();
            });
        }

        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {

            playerId = table.getValueAt(row,0).toString();
            playerName = table.getValueAt(row,1).toString();
            teamName = table.getValueAt(row,2).toString();
            gameName = table.getValueAt(row,3).toString();
            age = Integer.parseInt(table.getValueAt(row,4).toString());

            return panel;
        }

        // ================= SAVE SELECTED =================

        void saveSelectedPlayer() {

            try {

                Connection con = DBConnection.getConnection();

                String sql =
                        "INSERT INTO selected_players VALUES(?,?,?,?,?)";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1,playerId);
                pst.setString(2,playerName);
                pst.setString(3,teamName);
                pst.setString(4,gameName);
                pst.setInt(5,age);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        null,
                        "Player Selected!"
                );

                pst.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            fireEditingStopped();
        }

        // ================= SAVE REJECTED =================

        void saveRejectedPlayer() {

            try {

                Connection con = DBConnection.getConnection();

                String sql =
                        "INSERT INTO rejected_players VALUES(?,?,?,?,?)";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1,playerId);
                pst.setString(2,playerName);
                pst.setString(3,teamName);
                pst.setString(4,gameName);
                pst.setInt(5,age);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        null,
                        "Player Rejected!"
                );

                pst.close();
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            fireEditingStopped();
        }
    }

    // MAIN
    public static void main(String[] args) {

        new selectedplayer();
    }
}