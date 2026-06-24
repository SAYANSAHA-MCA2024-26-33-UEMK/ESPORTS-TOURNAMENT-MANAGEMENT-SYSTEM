import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class tournament {
    public static void main(String[] args) {
        new tour();
    }
}

class tour extends JFrame {

    tour() {

        setTitle("Tournament Management");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,10,10,10);

        // TITLE
        JLabel l1 = new JLabel("TOURNAMENT MANAGEMENT");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setForeground(new Color(139,69,19));

        // LABELS
        JLabel l2 = new JLabel("Tournament ID:");
        JLabel l3 = new JLabel("Tournament Name:");
        JLabel l4 = new JLabel("Game:");
        JLabel l5 = new JLabel("Start Date (DD-MM-YYYY):");
        JLabel l6 = new JLabel("Prize Pool:");

        // TEXTFIELDS
        JTextField t1 = new JTextField(15);
        JTextField t2 = new JTextField(15);
        JTextField t3 = new JTextField(15);
        JTextField t4 = new JTextField(15);
        JTextField t5 = new JTextField(15);

        // BUTTONS
        JButton submit = new JButton("Submit");
        JButton back = new JButton("Back");

        JButton[] buttons = {submit, back};

        for(JButton b : buttons){
            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(100,30));
        }

        // BUTTON PANEL
        JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));

        buttonPanel.add(submit);
        buttonPanel.add(back);

        // TITLE
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        panel.add(l1, g);

        g.gridwidth = 1;

        // TOURNAMENT ID
        g.gridx = 0;
        g.gridy = 1;
        panel.add(l2, g);

        g.gridx = 1;
        panel.add(t1, g);

        // TOURNAMENT NAME
        g.gridx = 0;
        g.gridy = 2;
        panel.add(l3, g);

        g.gridx = 1;
        panel.add(t2, g);

        // GAME
        g.gridx = 0;
        g.gridy = 3;
        panel.add(l4, g);

        g.gridx = 1;
        panel.add(t3, g);

        // START DATE
        g.gridx = 0;
        g.gridy = 4;
        panel.add(l5, g);

        g.gridx = 1;
        panel.add(t4, g);

        // PRIZE POOL
        g.gridx = 0;
        g.gridy = 5;
        panel.add(l6, g);

        g.gridx = 1;
        panel.add(t5, g);

        // BUTTONS
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 2;
        panel.add(buttonPanel, g);

        add(panel);

        // SUBMIT BUTTON
        submit.addActionListener(e -> {

            String id = t1.getText().trim();
            String name = t2.getText().trim();
            String game = t3.getText().trim();
            String dateText = t4.getText().trim();
            String prize = t5.getText().trim();

            // EMPTY FIELD CHECK
            if(id.isEmpty() || name.isEmpty() ||
                    game.isEmpty() || dateText.isEmpty() ||
                    prize.isEmpty()){

                JOptionPane.showMessageDialog(null,
                        "Please fill all fields!");
                return;
            }

            try {

                // DATE CONVERSION
                java.util.Date utilDate =
                        new SimpleDateFormat("dd-MM-yyyy")
                                .parse(dateText);

                java.sql.Date sqlDate =
                        new java.sql.Date(utilDate.getTime());

                // DATABASE CONNECTION
                Connection con = DBConnection.getConnection();

                if(con == null){
                    JOptionPane.showMessageDialog(null,
                            "Database not connected!");
                    return;
                }

                // INSERT QUERY
                String sql =
                        "INSERT INTO tournament VALUES(?,?,?,?,?)";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1, id);
                pst.setString(2, name);
                pst.setString(3, game);
                pst.setDate(4, sqlDate);
                pst.setString(5, prize);

                int result = pst.executeUpdate();

                if(result > 0){

                    JOptionPane.showMessageDialog(null,
                            "Tournament Added Successfully!");

                    // CLEAR FIELDS
                    t1.setText("");
                    t2.setText("");
                    t3.setText("");
                    t4.setText("");
                    t5.setText("");

                } else {

                    JOptionPane.showMessageDialog(null,
                            "Failed!");

                }

                pst.close();
                con.close();

            } catch (Exception ex){

                ex.printStackTrace();

                JOptionPane.showMessageDialog(null,
                        "Database Error!");

            }
        });

        // BACK BUTTON
        submit.addActionListener(e -> {
            new matchscheduler1();
            dispose();
        });


        back.addActionListener(e -> {
            new dash();
            dispose();
        });

        setSize(550,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}