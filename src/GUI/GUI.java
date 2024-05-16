package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.Graph.DrawGraphic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI {
    private MyFrame mainFrame;
    private firstFrame FirstFrame;
    private shortest_path frame2;
    private bridge_words frame3;
    private add_bridge_word frame4;
    private random_wonder frame5;
    private DrawGraphic d;

    public static void main(String[] args) {
        GUI g = new GUI();
    }

    public GUI() {
        mainFrame = new MyFrame("mainFrame", false);
        FirstFrame = new firstFrame();
        FirstFrame.setVisible(true);
        mainFrame.setVisible(false);
        

        FirstFrame.jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FirstFrame.dispose();
                String text_path = FirstFrame.getText_path();
                d = new DrawGraphic(text_path);
                init();
                mainFrame.setVisible(true);
            }
        });
    }

    public void init() {
        // 读取的文件路径为text_path
        // 获取核心绘图对象
        // 设置第二个跳转页面frame2
        frame2 = new shortest_path("shortest_path", true, d);
        frame2.setVisible(false);
        frame2.setMainFrame(mainFrame);
        JPanel frame2_panel = new JPanel();
        JButton button = new JButton("shortest_path");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame2.setTextLabelNull();
                frame2.setVisible(true); // 显示第二个窗口
                mainFrame.setVisible(false); // 隐藏第一个窗口
            }
        });

        // 设置第三个跳转页面frame3
        frame3 = new bridge_words("bridge_words", true, d);
        frame3.setVisible(false);
        frame3.setMainFrame(mainFrame);

        JPanel frame3_panel = new JPanel();
        JButton button3 = new JButton("bridge_words");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame3.setTextLabelNull();
                frame3.setVisible(true); // 显示第三个窗口
                mainFrame.setVisible(false); // 隐藏第一个窗口
            }
        });

        // 设置第四个跳转页面frame4
        frame4 = new add_bridge_word("add_bridge_word", true, d);
        frame4.setVisible(false);
        frame4.setMainFrame(mainFrame);

        JPanel frame4_panel = new JPanel();
        JButton button4 = new JButton("add_bridge_word");
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame4.setVisible(true); // 显示第四个窗口
                mainFrame.setVisible(false); // 隐藏第一个窗口
            }
        });

        // 设置第五个跳转页面frame5
        frame5 = new random_wonder("random_wonder", true, d);
        frame5.setVisible(false);
        frame5.setMainFrame(mainFrame);

        JPanel frame5_panel = new JPanel();
        JButton button5 = new JButton("random_wonder");
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame5.setVisible(true); // 显示第五个窗口
                mainFrame.setVisible(false); // 隐藏第一个窗口

                // 选择随机游走时，随选择一个节点作为开始节点
                // frame5.node_now = select_a_random_node();
            }
        });

        // 设置一下界面
        JPanel jPanel_center = new JPanel();
        JPanel jPanel_south = new JPanel();
        jPanel_south.add(button);
        jPanel_south.add(button3);
        jPanel_south.add(button4);
        jPanel_south.add(button5);
        jPanel_south.setPreferredSize(new Dimension(0, 150));
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(jPanel_south, BorderLayout.SOUTH);

        // 插入一张图片
        try {
            BufferedImage originalImage = ImageIO.read(new File("src/tmp.jpg"));

            // 缩放图片尺寸为400*300
            int scaledWidth = 800;
            int scaledHeight = 600;
            BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();
            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imgLabel = new JLabel(imageIcon);
            imgLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
            jPanel_center.add(imgLabel);
            mainFrame.add(jPanel_center, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
