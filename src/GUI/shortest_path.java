package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;

import src.Graph.DrawGraphic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class shortest_path extends JFrame {
    JFrame mainFrame;
    String result;
    JLabel textLabel;

    private DrawGraphic d;

    List<String> list_result;

    public shortest_path(String title, boolean ifBack, DrawGraphic d) {
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
            jPanel.add(jButton1, BorderLayout.NORTH);
            jPanel_NORTH.add(jPanel, BorderLayout.WEST);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);

        // 设置节点输入框
        JTextField jTextField1 = new JTextField(20);
        JTextField jTextField2 = new JTextField(20);
        jTextField1.setFont(font);
        jTextField2.setFont(font);
        jTextField2.setPreferredSize(new Dimension(150, 100));
        jPanel_CENTER.add(jTextField1, BorderLayout.WEST);
        jPanel_CENTER.add(jTextField2, BorderLayout.CENTER);

        // 插入一个展示结果的文本Label
        textLabel = new JLabel();
        textLabel.setText(" ");
        textLabel.setText(this.result);
        textLabel.setFont(font);
        jPanel_SOUTH.add(textLabel, BorderLayout.CENTER);
        jPanel_SOUTH.setPreferredSize(new Dimension(0, 100));

        // 设置开始按钮
        JButton jButton = new JButton("Start Searching");
        jButton.setFont(font);
        JComboBox<String> comboBox = new JComboBox<>();
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = jTextField1.getText();
                String end = jTextField2.getText();
                System.out.println("开始节点为：" + start);
                System.out.println("结束节点为：" + end);
                // 这里获取到开始节点与结束节点的信息了，调用获取结果存入result即可

                // 如果end不为空
                if (!Objects.equals(end, "")) {
                    jTextField1.setText("");
                    jTextField2.setText("");
                    String result = d.calcShortestPath(start, end);
                    if (!result.equals("Fail to find the min source")) {
                        try {
                            String name = result.split(":")[0].split(" ")[1];
                            BufferedImage originalImage = ImageIO.read(new File("./src/pic/tmp_" + name + ".jpg"));
                            ImageIcon imageIcon = new ImageIcon(originalImage);
                            JLabel imgLabel = new JLabel(imageIcon);
                            JScrollPane scrollPane = new JScrollPane(imgLabel);
                            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                            scrollPane.setPreferredSize(new Dimension(800, 650));
                            jPanel_NORTH.remove(1);
                            jPanel_NORTH.add(scrollPane, BorderLayout.CENTER);
                            shortest_path.this.add(jPanel_NORTH, BorderLayout.NORTH);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    textLabel.setText(result);
                }
                // 如果end为空
                else {

                    textLabel.setText("");
                    shortest_path.this.list_result = shortest_path.this.d.calcShortestPath(start);
                    List<String> list_end = new ArrayList<>();
                    for (String s : shortest_path.this.list_result) {
                        list_end.add(s.split(":")[0].split(" ")[1]);
                    }
                    comboBox.removeAllItems();
                    for (String string : list_end) {
                        comboBox.addItem(string);
                    }
                    comboBox.setPreferredSize(new Dimension(80, 20));
                    comboBox.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            if (e.getStateChange() == ItemEvent.SELECTED) {
                                // 检查哪个选项被选中
                                String selectedItem = (String) comboBox.getSelectedItem();
                                System.out.println("Selected item: " + selectedItem);
                                for (String i : shortest_path.this.list_result) {
                                    if (i.split(":")[0].split(" ")[1].equals(selectedItem)) {
                                        textLabel.setText(i);
                                        try {
                                            BufferedImage originalImage = ImageIO
                                                    .read(new File("./src/pic/tmp_" + selectedItem + ".jpg"));
                                            ImageIcon imageIcon = new ImageIcon(originalImage);
                                            JLabel imgLabel = new JLabel(imageIcon);
                                            JScrollPane scrollPane = new JScrollPane(imgLabel);
                                            scrollPane
                                                    .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                                            scrollPane.setHorizontalScrollBarPolicy(
                                                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                                            scrollPane.setPreferredSize(new Dimension(800, 650));
                                            jPanel_NORTH.remove(1);
                                            jPanel_NORTH.add(scrollPane, BorderLayout.CENTER);
                                            shortest_path.this.add(jPanel_NORTH, BorderLayout.NORTH);
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    });
                    jPanel_SOUTH.add(comboBox, BorderLayout.WEST);
                    jPanel_SOUTH.revalidate();
                    jPanel_SOUTH.repaint();
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
        this.add(jPanel_NORTH, BorderLayout.NORTH);
        this.add(jPanel_SOUTH, BorderLayout.SOUTH);
        this.add(jPanel_CENTER, BorderLayout.CENTER);
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
