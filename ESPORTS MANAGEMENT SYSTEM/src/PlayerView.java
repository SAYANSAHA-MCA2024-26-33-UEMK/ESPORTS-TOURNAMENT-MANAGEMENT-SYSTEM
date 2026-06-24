import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerView extends JFrame {

    JTable table;

    PlayerView() {

        setTitle("Player Details");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ⚪ MAIN PANEL (WHITE BACKGROUND)
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 🎯 TITLE
        JLabel title = new JLabel("PLAYER DETAILS");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(147, 41, 3));
        title.setHorizontalAlignment(JLabel.CENTER);

        panel.add(title, BorderLayout.NORTH);

        // TABLE COLUMNS
        String[] columns = {
                "Player ID",
                "Player Name",
                "Team Name",
                "Game Name",
                "Age"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        // 🎨 TABLE STYLE (CLEAN LOOK)
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);

        // 📌 CENTER ALIGN DATA
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // 🔵 HEADER COLOR = (0,0,139)
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0, 0, 139));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);
        panel.add(sp, BorderLayout.CENTER);

        // 🔵 BUTTON PANEL
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        JButton back = new JButton("BACK");

        back.setBackground(new Color(0, 0, 139));
        back.setForeground(Color.WHITE);
        back.setFont(new Font("Arial", Font.BOLD, 14));
        back.setFocusPainted(false);
        back.setPreferredSize(new Dimension(120, 35));

        btnPanel.add(back);

        panel.add(btnPanel, BorderLayout.SOUTH);

        // 📥 LOAD DATA
        loadData(model);

        // 🔙 BACK BUTTON
        back.addActionListener(e -> {
            new dash();
            dispose();
        });

        add(panel);
        setVisible(true);
    }

    // 📊 FETCH DATA FROM DATABASE
    void loadData(DefaultTableModel model) {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM player";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

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
            JOptionPane.showMessageDialog(this, "Database Error!");
        }
    }

    public static void main(String[] args) {
        new PlayerView();
    }
}