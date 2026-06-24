import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class registration {
    public static void main(String[] args) {
        new regis();
    }
}

class regis extends JFrame {

    regis() {

        setTitle("Student Registration");

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10,10,10,10);

        // TITLE
        JLabel l1 = new JLabel("REGISTRATION");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setForeground(new Color(139,69,19));

        // LABELS
        JLabel l2 = new JLabel("Name:");
        JLabel l3 = new JLabel("Email:");
        JLabel l4 = new JLabel("Username:");
        JLabel l5 = new JLabel("Password:");
        JLabel l6 = new JLabel("Role:");

        // TEXTFIELDS
        JTextField t1 = new JTextField(15); // Name
        JTextField t2 = new JTextField(15); // Email
        JTextField t3 = new JTextField(15); // Username
        JPasswordField t4 = new JPasswordField(15); // Password

        // ROLE DROPDOWN
        JComboBox<String> roleBox =
                new JComboBox<>(new String[]{"Player","Staff"});

        // BUTTONS
        JButton register = new JButton("Register");
        JButton back = new JButton("Back");

        JButton[] buttons = {register, back};

        for(JButton b : buttons){
            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(100,30));
        }

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        buttonPanel.add(register);
        buttonPanel.add(back);

        // TITLE
        g.gridx = 0;
        g.gridy = 0;
        g.gridwidth = 2;
        panel.add(l1, g);

        g.gridwidth = 1;

        // NAME
        g.gridx = 0;
        g.gridy = 1;
        panel.add(l2, g);

        g.gridx = 1;
        panel.add(t1, g);

        // EMAIL
        g.gridx = 0;
        g.gridy = 2;
        panel.add(l3, g);

        g.gridx = 1;
        panel.add(t2, g);

        // USERNAME
        g.gridx = 0;
        g.gridy = 3;
        panel.add(l4, g);

        g.gridx = 1;
        panel.add(t3, g);

        // PASSWORD
        g.gridx = 0;
        g.gridy = 4;
        panel.add(l5, g);

        g.gridx = 1;
        panel.add(t4, g);

        // ROLE
        g.gridx = 0;
        g.gridy = 5;
        panel.add(l6, g);

        g.gridx = 1;
        panel.add(roleBox, g);

        // BUTTONS
        g.gridx = 0;
        g.gridy = 6;
        g.gridwidth = 2;
        panel.add(buttonPanel, g);

        add(panel);

        // REGISTER BUTTON
        register.addActionListener(e -> {

            String name = t1.getText().trim();
            String email = t2.getText().trim();
            String username = t3.getText().trim();
            String password = new String(t4.getPassword()).trim();
            String role = roleBox.getSelectedItem().toString();

            // EMPTY FIELD CHECK
            if(name.isEmpty() || email.isEmpty() ||
                    username.isEmpty() || password.isEmpty()){

                JOptionPane.showMessageDialog(null,
                        "Please fill all fields!");
                return;
            }

            try {

                Connection con = DBConnection.getConnection();

                // DATABASE CHECK
                if(con == null){
                    JOptionPane.showMessageDialog(null,
                            "Database not connected!");
                    return;
                }

                // INSERT QUERY
                String sql = "INSERT INTO players VALUES(?,?,?,?,?)";

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, name);
                pst.setString(2, email);
                pst.setString(3, username);
                pst.setString(4, password);
                pst.setString(5, role);

                int result = pst.executeUpdate();

                if(result > 0){

                    JOptionPane.showMessageDialog(null,
                            "Registration Successful!");

                    new playlog1();
                    dispose();

                } else {

                    JOptionPane.showMessageDialog(null,
                            "Registration Failed!");

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
        back.addActionListener(e -> {
            new playlog1();
            dispose();
        });

        setSize(450,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}