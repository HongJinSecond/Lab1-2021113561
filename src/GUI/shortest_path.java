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

public class shortest_path extends JFrame {
    JFrame mainFrame;
    String result;
    JLabel textLabel;
    private DrawGraphic d;

    public shortest_path(String title, boolean ifBack, DrawGraphic d) {
        super(title);
        this.d = d;

        //设置字体
        Font font = new Font("Microsoft YaHei",Font.BOLD,24);

        // 设置三个区域的Panel
        JPanel jPanel_NORTH = new JPanel();
        JPanel jPanel_CENTER = new JPanel();
        JPanel jPanel_SOUTH = new JPanel();
        jPanel_NORTH.setLayout(new BorderLayout());
        jPanel_SOUTH.setLayout(new BorderLayout());
        jPanel_CENTER.setLayout(new BorderLayout());

        this.setLayout(new BorderLayout());
        if (ifBack) {
            JButton jButton1 = new JButton("BACK");
            jButton1.setFont(font);
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());

            jButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    mainFrame.setVisible(true);
                }
            });
            jPanel.add(jButton1,BorderLayout.NORTH);
            jPanel_NORTH.add(jPanel, BorderLayout.WEST);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);

        // 设置节点输入框
        JTextField jTextField1 = new JTextField(20);
        JTextField jTextField2 = new JTextField(20);
        jTextField1.setFont(font);
        jTextField2.setFont(font);
        jTextField2.setPreferredSize(new Dimension(150,100));
        jPanel_CENTER.add(jTextField1, BorderLayout.WEST);
        jPanel_CENTER.add(jTextField2, BorderLayout.CENTER);

        // 插入一个展示结果的文本Label
        textLabel = new JLabel();
        textLabel.setText(" ");
        textLabel.setText(this.result);
        textLabel.setFont(font);
        System.out.println(this.result);
        jPanel_SOUTH.add(textLabel, BorderLayout.CENTER);
        jPanel_SOUTH.setPreferredSize(new Dimension(0, 100));
        this.add(jPanel_SOUTH, "South");

        // 设置开始按钮
        JButton jButton = new JButton("Start Searching");
        jButton.setFont(font);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = jTextField1.getText();
                String end = jTextField2.getText();
                System.out.println("开始节点为：" + start);
                System.out.println("结束节点为：" + end);
                // 这里获取到开始节点与结束节点的信息了，调用获取结果存入result即可

                shortest_path.this.result = start + "到" + end;
                jTextField1.setText("");
                jTextField2.setText("");
                String result = d.findMinSource(start, end);
                if (result.equals("success")) {
                    try {
                        BufferedImage originalImage = ImageIO.read(new File("src/tmp_s.jpg"));

                        // 缩放图片尺寸为400*300
                        int scaledWidth = 800;
                        int scaledHeight = 600;
                        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight,
                                BufferedImage.TYPE_INT_ARGB);
                        Graphics2D g2d = scaledImage.createGraphics();
                        g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
                        g2d.dispose();

                        ImageIcon imageIcon = new ImageIcon(scaledImage);
                        JLabel imgLabel = new JLabel(imageIcon);
                        imgLabel.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
                        jPanel_NORTH.remove(1);
                        jPanel_NORTH.add(imgLabel, BorderLayout.CENTER);
                        shortest_path.this.add(jPanel_NORTH, BorderLayout.NORTH);
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                textLabel.setText(result);
            }
        });
        jPanel_CENTER.add(jButton, BorderLayout.EAST);
        this.add(jPanel_CENTER, BorderLayout.CENTER);

        // 插入一张图片
        try {
            BufferedImage originalImage = ImageIO.read(new File("src/tmp.jpg"));

            // 缩放图片尺寸
            int scaledWidth = 800;
            int scaledHeight = 600;
            BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = scaledImage.createGraphics();
            g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            JLabel imgLabel2 = new JLabel(imageIcon);
            imgLabel2.setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());
            jPanel_NORTH.add(imgLabel2, BorderLayout.CENTER);
            this.add(jPanel_NORTH, BorderLayout.NORTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPanel(JPanel panel) {
        this.setContentPane(panel);
    }

    public String getResult() {
        return this.result;
    }

    public void setTextLabelNull() {
        shortest_path.this.textLabel.setText("");
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
