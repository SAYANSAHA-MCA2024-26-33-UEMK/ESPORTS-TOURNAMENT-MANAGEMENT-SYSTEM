import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class stafflogin {
    public static void main(String[] args) {
        new staffl1();
    }
}

class staffl1 extends JFrame {

    staffl1() {

        setTitle("Staff Login");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,10,10,10);

        // TITLE
        JLabel l1 = new JLabel("STAFF LOGIN");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setForeground(new Color(139,69,19));

        // LABELS
        JLabel l2 = new JLabel("Username:");
        JLabel l3 = new JLabel("Password:");

        // TEXTFIELDS
        JTextField t1 = new JTextField(15);
        JPasswordField t2 = new JPasswordField(15);

        // BUTTONS
        JButton login = new JButton("Login");
        JButton back = new JButton("Back");

        JButton[] buttons = {login, back};

        for(JButton b : buttons){
            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(100,30));
        }

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        buttonPanel.add(login);
        buttonPanel.add(back);

        // TITLE
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        panel.add(l1, g);

        // USERNAME
        g.gridwidth = 1;
        g.gridx = 0;
        g.gridy = 1;
        panel.add(l2, g);

        g.gridx = 1;
        panel.add(t1, g);

        // PASSWORD
        g.gridx = 0;
        g.gridy = 2;
        panel.add(l3, g);

        g.gridx = 1;
        panel.add(t2, g);

        // BUTTONS
        g.gridx = 0;
        g.gridy = 3;
        g.gridwidth = 2;
        panel.add(buttonPanel, g);

        add(panel);

        // LOGIN BUTTON
        login.addActionListener(e -> {

            String username = t1.getText().trim();
            String password = new String(t2.getPassword()).trim();

            // EMPTY FIELD CHECK
            if(username.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all fields!");
                return;
            }

            try {

                Connection con = DBConnection.getConnection();

                // DATABASE CHECK
                if(con == null){
                    JOptionPane.showMessageDialog(null, "Database not connected!");
                    return;
                }

                // SQL QUERY
                String sql = "SELECT * FROM staff WHERE username=? AND password=?";

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs = pst.executeQuery();

                // LOGIN SUCCESS
                if(rs.next()){

                    JOptionPane.showMessageDialog(null, "Login Successful!");

                    new selectedplayer();
                    dispose();

                } else {

                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!");

                }

                // CLOSE CONNECTION
                rs.close();
                pst.close();
                con.close();

            } catch (Exception ex){

                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database Error!");

            }
        });

        // BACK BUTTON
        back.addActionListener(e -> {
            new home1();
            dispose();
        });

        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}