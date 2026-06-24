import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class player {

    public static void main(String[] args) {

        new play();
    }
}

class play extends JFrame {

    JTextField t1, t2, t3, t4, t5;

    play() {

        setTitle("Player Management");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,10,10,10);

        // TITLE
        JLabel l1 = new JLabel("PLAYER MANAGEMENT");
        l1.setFont(new Font("Arial", Font.BOLD, 24));
        l1.setForeground(new Color(147, 41, 3));

        // LABELS
        JLabel l2 = new JLabel("Player ID:");
        JLabel l3 = new JLabel("Player Name:");
        JLabel l4 = new JLabel("Team Name:");
        JLabel l5 = new JLabel("Game Name:");
        JLabel l6 = new JLabel("Age:");

        l2.setForeground(new Color(5, 5, 5));
        l3.setForeground(new Color(5, 5, 5));
        l4.setForeground(new Color(5, 5, 5));
        l5.setForeground(new Color(5, 5, 5));
        l6.setForeground(new Color(5, 5, 5));

        // TEXTFIELDS
        t1 = new JTextField(15);
        t2 = new JTextField(15);
        t3 = new JTextField(15);
        t4 = new JTextField(15);
        t5 = new JTextField(15);

        // BUTTONS
        JButton submit = new JButton("Submit");
        JButton back = new JButton("Back");

        JButton[] buttons = {submit, back};

        for(JButton b : buttons){

            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(120,35));
        }

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        btnPanel.add(submit);
        btnPanel.add(back);

        // TITLE
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        panel.add(l1,g);

        g.gridwidth = 1;

        // PLAYER ID
        g.gridx = 0;
        g.gridy = 1;
        panel.add(l2,g);

        g.gridx = 1;
        panel.add(t1,g);

        // PLAYER NAME
        g.gridx = 0;
        g.gridy = 2;
        panel.add(l3,g);

        g.gridx = 1;
        panel.add(t2,g);

        // TEAM NAME
        g.gridx = 0;
        g.gridy = 3;
        panel.add(l4,g);

        g.gridx = 1;
        panel.add(t3,g);

        // GAME NAME
        g.gridx = 0;
        g.gridy = 4;
        panel.add(l5,g);

        g.gridx = 1;
        panel.add(t4,g);

        // AGE
        g.gridx = 0;
        g.gridy = 5;
        panel.add(l6,g);

        g.gridx = 1;
        panel.add(t5,g);

        // BUTTON PANEL
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 2;

        panel.add(btnPanel,g);

        add(panel);

        // ================= SUBMIT BUTTON =================

        submit.addActionListener(e -> {

            String id = t1.getText().trim();
            String name = t2.getText().trim();
            String team = t3.getText().trim();
            String game = t4.getText().trim();
            String age = t5.getText().trim();

            // EMPTY CHECK
            if(id.isEmpty() || name.isEmpty() || team.isEmpty()
                    || game.isEmpty() || age.isEmpty()){

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields!"
                );

                return;
            }

            try {

                Connection con = DBConnection.getConnection();

                String sql =
                        "INSERT INTO player(player_id,player_name,team_name,game_name,age,status) VALUES (?,?,?,?,?,?)";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1,id);
                pst.setString(2,name);
                pst.setString(3,team);
                pst.setString(4,game);
                pst.setInt(5,Integer.parseInt(age));
                pst.setString(6,"PENDING");

                pst.executeUpdate();

                JOptionPane.showMessageDialog(
                        this,
                        "Player Added Successfully!"
                );

                pst.close();
                con.close();

                clearFields();

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error!"
                );
            }
        });

        // ================= BACK BUTTON =================

        submit.addActionListener(e -> {

            new dash();
            dispose();
        });

        back.addActionListener(e -> {

            new playlog1();
            dispose();
        });

        setSize(500,450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ================= CLEAR FIELDS =================

    void clearFields(){

        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
    }
}