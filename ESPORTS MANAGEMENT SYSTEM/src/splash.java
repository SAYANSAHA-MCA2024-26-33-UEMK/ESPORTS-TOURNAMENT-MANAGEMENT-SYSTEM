import javax.swing.*;

public class splash {
    public static void main(String[] args) {
        new splash1();
    }
}

class splash1 extends JFrame {

    splash1(){

        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("icon/sports.gif"));
        JLabel label = new JLabel(imageIcon);
        add(label);

        setSize(650,600);
        setLocationRelativeTo(null);
        setVisible(true);

        try{
            Thread.sleep(2000);
            new home1();
            dispose();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}