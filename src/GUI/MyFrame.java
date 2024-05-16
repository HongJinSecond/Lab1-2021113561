package src.GUI;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame {
    static JFrame mainFrame;

    public MyFrame(String title, boolean ifBack) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);
    }

    public void setPanel(JPanel panel) {
        this.setContentPane(panel);
    }
}
