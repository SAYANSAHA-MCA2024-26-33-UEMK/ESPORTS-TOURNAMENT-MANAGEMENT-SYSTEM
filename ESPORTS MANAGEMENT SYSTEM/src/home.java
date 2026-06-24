import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home {
    public static void main(String[] args) {
        new home1();
    }
}

class home1 extends JFrame {

    public home1() {

        setTitle("Esports Management System");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("ESPORTS MANAGEMENT SYSTEM");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setForeground(new Color(139,69,19));
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        JButton b1 = new JButton("Admin");
        JButton b2 = new JButton("Staff");
        JButton b3 = new JButton("Player");

        JButton[] buttons = {b1,b2,b3};

        for(JButton b : buttons){
            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setFocusPainted(false);
            b.setPreferredSize(new Dimension(110,35));
        }

        // Admin button action
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new adminl1();
                dispose();
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new staffl1();
                dispose();
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new playlog1();
                dispose();
            }
        });

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(l1);
        mainPanel.add(Box.createRigidArea(new Dimension(0,20)));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel);

        setSize(500,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}