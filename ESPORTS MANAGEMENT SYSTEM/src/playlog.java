import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class playlog {
    public static void main(String[] args) {
        new playlog1();
    }
}

class playlog1 extends JFrame {

    playlog1() {

        setTitle("Player Login");

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,10,10,10);

        // TITLE
        JLabel title = new JLabel("PLAYER LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(new Color(139,69,19));

        // LABELS
        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Password:");

        // TEXTFIELDS
        JTextField t1 = new JTextField(15);
        JPasswordField t2 = new JPasswordField(15);

        // TEXT
        JLabel info =
                new JLabel("If you are not registered then click Register");

        info.setForeground(Color.GRAY);

        // BUTTONS
        JButton login = new JButton("Login");
        JButton register = new JButton("Register");
        JButton back = new JButton("Back");

        JButton[] buttons = {login, register, back};

        for(JButton b : buttons){

            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(100,30));

        }

        // BUTTON PANEL
        JPanel btnPanel = new JPanel();

        btnPanel.add(login);
        btnPanel.add(register);
        btnPanel.add(back);

        // TITLE
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        panel.add(title, g);

        // USERNAME
        g.gridwidth = 1;

        g.gridx = 0;
        g.gridy = 1;
        panel.add(l1, g);

        g.gridx = 1;
        panel.add(t1, g);

        // PASSWORD
        g.gridx = 0;
        g.gridy = 2;
        panel.add(l2, g);

        g.gridx = 1;
        panel.add(t2, g);

        // INFO TEXT
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        panel.add(info, g);

        // BUTTONS
        g.gridx = 0;
        g.gridy = 4;
        g.gridwidth = 2;
        panel.add(btnPanel, g);

        add(panel);

        // LOGIN BUTTON
        login.addActionListener(e -> {

            String username = t1.getText().trim();
            String password = new String(t2.getPassword()).trim();

            // EMPTY FIELD CHECK
            if(username.isEmpty() || password.isEmpty()){

                JOptionPane.showMessageDialog(
                        null,
                        "Please fill all fields!"
                );

                return;
            }

            try {

                // DATABASE CONNECTION
                Connection con = DBConnection.getConnection();

                // CHECK CONNECTION
                if(con == null){

                    JOptionPane.showMessageDialog(
                            null,
                            "Database not connected!"
                    );

                    return;
                }

                // SQL QUERY
                String sql =
                        "SELECT * FROM players WHERE username=? AND password=? AND role='Player'";

                PreparedStatement pst =
                        con.prepareStatement(sql);

                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                // LOGIN SUCCESS
                if(rs.next()){

                    JOptionPane.showMessageDialog(
                            null,
                            "Login Successful!"
                    );

                    new dash();
                    dispose();

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid Username or Password!"
                    );

                }

                // CLOSE CONNECTION
                rs.close();
                pst.close();
                con.close();

            } catch (Exception ex){

                ex.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Database Error!"
                );

            }
        });

        // REGISTER BUTTON
        register.addActionListener(e -> {

            new regis();
            dispose();

        });

        // BACK BUTTON
        back.addActionListener(e -> {

            new home1();
            dispose();

        });

        setSize(500,320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}