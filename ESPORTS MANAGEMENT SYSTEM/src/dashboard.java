import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboard {
    public static void main(String[] args) {
        new dash();
    }
}

class dash extends JFrame {

    public dash() {

        setTitle("Dashboard");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel l1 = new JLabel("DASHBOARD");
        l1.setFont(new Font("Arial", Font.BOLD, 22));
        l1.setForeground(new Color(131, 34, 3));
        l1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        //buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));


        JButton b1 = new JButton("Tournament");
        JButton b2 = new JButton("Player Management");
        JButton b3 = new JButton("Team Management");
        JButton b4 = new JButton("Match Schedule");
        JButton b5 = new JButton("Results");

        JButton[] buttons = {b1,b2,b3,b4,b5};

        for(JButton b : buttons){
            b.setBackground(new Color(0,0,139));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Arial", Font.BOLD, 14));
            b.setFocusPainted(false);
            //b.setPreferredSize(new Dimension(200,35));
            b.setMaximumSize(new Dimension(200,35)); // important
            b.setAlignmentX(Component.CENTER_ALIGNMENT);

            buttonPanel.add(b);
            // GAP BETWEEN BUTTONS
           // buttonPanel.add(Box.createVerticalStrut(25));
        }

        // Admin button action
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TournamentData();
                dispose();
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PlayerView();
                dispose();
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new selectedplayershow();
                dispose();
            }
        });

        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new matchsview1();
                dispose();
            }
        });

        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new matchesresult1();
                dispose();
            }
        });

        buttonPanel.add(b1);
        buttonPanel.add(b2);
        buttonPanel.add(b3);
        buttonPanel.add(b4);
        buttonPanel.add(b5);

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