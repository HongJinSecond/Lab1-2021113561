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
import java.util.List;

public class bridge_words extends JFrame {
    JFrame mainFrame;
    String result;
    JLabel textLabel;

    private DrawGraphic d;

    public bridge_words(String title, boolean ifBack, DrawGraphic d) {
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

        if (ifBack) {
            JButton jButton = new JButton("BACK");
            jButton.setFont(font);
            JPanel jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());

            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    mainFrame.setVisible(true);
                }
            });
            jPanel.add(jButton, BorderLayout.NORTH);
            jPanel_NORTH.add(jPanel, BorderLayout.WEST);
            this.add(jPanel_NORTH, BorderLayout.NORTH);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);

        // 设置节点输入框
        JTextField jTextField1 = new JTextField(20);
        JTextField jTextField2 = new JTextField(20);
        jTextField1.setFont(font);
        jTextField2.setFont(font);
        jPanel_CENTER.add(jTextField1, BorderLayout.WEST);
        jPanel_CENTER.add(jTextField2, BorderLayout.CENTER);

        // 插入一个展示结果的文本Label
        textLabel = new JLabel();
        textLabel.setText("");
        textLabel.setSize(80, 40);
        textLabel.setText(this.result);
        textLabel.setFont(font);
        jPanel_SOUTH.setPreferredSize(new Dimension(0, 100));
        jPanel_SOUTH.add(textLabel, BorderLayout.CENTER);

        // 设置开始按钮
        JButton jButton = new JButton("Start Searching");
        jButton.setFont(font);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = jTextField1.getText();
                String end = jTextField2.getText();
                System.out.println("开始词为：" + start);
                System.out.println("结束词为：" + end);
                // 这里获取到开始节点与结束节点的信息了，调用获取结果存入result即可

                jTextField1.setText("");
                jTextField2.setText("");

                List<String> strings = d.queryBridgeWords(start, end);
                StringBuilder builder = new StringBuilder();
                if (strings.size() == 0) {
                    textLabel.setText(String.format("找不到%s和%s间的桥接词", start, end));
                } else {
                    builder.append(start + "---->" + end + "的桥接词是:\n");
                    for (String s : strings) {
                        builder.append(s + ",");
                    }
                    textLabel.setText(builder.toString());
                }

            }
        });
        jPanel_CENTER.add(jButton, BorderLayout.EAST);

        // 插入一张图片
        try {
            BufferedImage originalImage = ImageIO.read(new File("./src/pic/tmp.jpg"));
            ImageIcon imageIcon = new ImageIcon(originalImage);
            JLabel imgLabel2 = new JLabel(imageIcon);
            JScrollPane scrollPane = new JScrollPane(imgLabel2);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scrollPane.setPreferredSize(new Dimension(800, 650));
            jPanel_NORTH.add(scrollPane, BorderLayout.CENTER);
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
        bridge_words.this.textLabel.setText("");
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
