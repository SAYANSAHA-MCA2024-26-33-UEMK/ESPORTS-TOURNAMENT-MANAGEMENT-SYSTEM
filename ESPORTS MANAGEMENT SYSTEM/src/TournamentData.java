import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TournamentData extends JFrame {

    JTable table;
    JButton apply, back;

    TournamentData() {

        setTitle("Tournament Data");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245,245,245));

        // TITLE
        JLabel title = new JLabel("TOURNAMENT LIST");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(131,34,3));
        title.setHorizontalAlignment(JLabel.CENTER);

        panel.add(title, BorderLayout.NORTH);

        // TABLE COLUMNS
        String[] column = {
                "Tournament ID",
                "Tournament Name",
                "Game",
                "Start Date",
                "Prize Pool"
        };

        DefaultTableModel model = new DefaultTableModel(column,0);

        // LOAD DATA FROM DB
        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM tournament";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                });
            }

            rs.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TABLE
        table = new JTable(model);

        table.setRowHeight(30);

        table.setFont(new Font("Arial", Font.PLAIN, 14));

        table.setSelectionBackground(new Color(0,0,139));

        table.setSelectionForeground(Color.WHITE);

// COLUMN HEADER COLOR
        table.getTableHeader().setBackground(new Color(0,0,139));

        table.getTableHeader().setForeground(Color.WHITE);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane sp = new JScrollPane(table);

        // BUTTONS
        apply = new JButton("APPLY");
        back = new JButton("BACK");

        JButton[] buttons = {apply, back};

        for (JButton b : buttons) {
            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(120,35));
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(245,245,245));
        btnPanel.add(apply);
        btnPanel.add(back);

        // ================= APPLY BUTTON =================
        apply.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a tournament first!");
                return;
            }

            String id = table.getValueAt(row, 0).toString();
            String name = table.getValueAt(row, 1).toString();
            String game = table.getValueAt(row, 2).toString();
            String date = table.getValueAt(row, 3).toString();
            String prize = table.getValueAt(row, 4).toString();

            try {

                Connection con = DBConnection.getConnection();

                String sql = "INSERT INTO applied_tournaments VALUES (NULL,?,?,?,?,?)";

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, id);
                pst.setString(2, name);
                pst.setString(3, game);
                pst.setString(4, date);
                pst.setString(5, prize);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Applied Successfully for " + name);

                pst.close();
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // ================= BACK BUTTON =================
        apply.addActionListener(e -> {

            JOptionPane.showMessageDialog(null,"Opening Player Page...");

            Timer timer = new Timer(2000, a -> {

                new play();
                dispose();

            });

            timer.setRepeats(false);
            timer.start();

        });

        back.addActionListener(e -> {

            new dash();   // go to dashboard
            dispose();

        });

        panel.add(sp, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new TournamentData();
    }
}