package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.Graph.DrawGraphic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class random_wonder extends JFrame {
    JFrame mainFrame;
    String result;
    String node_now;
    JLabel textLabel;
    private DrawGraphic d;

    public random_wonder(String title, boolean ifBack, DrawGraphic d) {
        // 设置标题
        super(title);
        this.d = d;

        // 设置字体
        Font font = new Font("Microsoft YaHei", Font.BOLD, 24);

        // 设置三个区域的Panel
        JPanel jPanel_NORTH = new JPanel();
        JPanel jPanel_CENTER = new JPanel();
        JPanel jPanel_SOUTH = new JPanel();
        jPanel_NORTH.setLayout(new BorderLayout());
        jPanel_SOUTH.setLayout(new BorderLayout());
        jPanel_CENTER.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());

        // 设置一个返回到mainFrame的按钮
        if (ifBack) {
            JButton jButton = new JButton("BACK");
            jButton.setFont(font);
            JPanel jPanel = new JPanel();

            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    d.freshRandomWalk();
                    mainFrame.setVisible(true);
                }
            });
            jPanel_NORTH.add(jButton, BorderLayout.WEST);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);

        // 插入一个展示结果的文本Label
        textLabel = new JLabel();
        textLabel.setFont(font);
        textLabel.setSize(80, 40);
        textLabel.setText(this.result);
        textLabel.setPreferredSize(new Dimension(0, 150));
        jPanel_SOUTH.add(textLabel, BorderLayout.CENTER);

        // 以及存储所有结果的stringBuilder
        StringBuilder stringBuilder = new StringBuilder();

        // 设置下一步按钮
        JButton jButton = new JButton("Start Searching");
        jButton.setFont(font);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // 让node_now获取返回的结果
                node_now = d.randomWalk();

                if (random_wonder.this.node_now != null) {
                    // 每次按钮，stringBuilder就多连接一个字符串
                    stringBuilder.append(node_now).append("->");
                    // 这里获取到随机游走节点的信息了，存入result即可
                    random_wonder.this.result = stringBuilder.toString();
                    textLabel.setText(random_wonder.this.node_now);
                } else {
                    textLabel.setText("当前随机游走已经结束力");
                }

            }
        });
        jPanel_CENTER.add(jButton, BorderLayout.WEST);

        // 设置写入文件按钮
        JButton jButton2 = new JButton("save as file");
        jButton2.setFont(font);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (FileWriter writer = new FileWriter("out.txt")) {
                    writer.write(random_wonder.this.result);
                    d.freshRandomWalk();
                    System.out.println("成功写入文件！");
                } catch (IOException e2) {
                    e2.printStackTrace();
                    System.out.println("写入文件时发生错误！");
                }
            }
        });
        jPanel_CENTER.add(jButton2, BorderLayout.EAST);

        // 插入一张图片
        try {
            BufferedImage originalImage = ImageIO.read(new File("./src/pic/tmp.jpg"));
            ImageIcon imageIcon = new ImageIcon(originalImage);
            JLabel imgLabel2 = new JLabel(imageIcon);
            JScrollPane scrollPane = new JScrollPane(imgLabel2);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(800, 650));
            jPanel_CENTER.add(scrollPane, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.add(jPanel_CENTER, BorderLayout.CENTER);
        this.add(jPanel_NORTH, BorderLayout.NORTH);
        this.add(jPanel_SOUTH, BorderLayout.SOUTH);
    }

    public void setPanel(JPanel panel) {
        this.setContentPane(panel);
    }

    public String getResult() {
        return this.result;
    }

    public void setTextLabelNull() {
        random_wonder.this.textLabel.setText("");
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
