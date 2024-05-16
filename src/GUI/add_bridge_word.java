/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-16 10:22:28
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-16 12:00:39
 */
package src.GUI;

import javax.swing.*;

import src.Graph.DrawGraphic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class add_bridge_word extends JFrame {
    JFrame mainFrame;
    String result;
    JLabel textLabel;

    private DrawGraphic d;

    public add_bridge_word(String title, boolean ifBack, DrawGraphic d) {
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
            jPanel.add(jButton,BorderLayout.NORTH);
            jPanel_NORTH.add(jPanel, BorderLayout.WEST);
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 900);

        // 设置输入框
        JTextField jTextField1 = new JTextField(20);
        jTextField1.setFont(font);
        jTextField1.setPreferredSize(new Dimension(400,200));
        jPanel_CENTER.add(jTextField1, BorderLayout.CENTER);

        // 插入一个展示结果的文本Label
        textLabel = new JLabel();
        textLabel.setText(" ");
        textLabel.setSize(80, 40);
        textLabel.setText(this.result);
        textLabel.setFont(font);
        System.out.println(this.result);
        jPanel_SOUTH.add(textLabel, BorderLayout.CENTER);

        // 设置开始按钮
        JButton jButton = new JButton("Start");
        jButton.setFont(font);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textToSwitch = jTextField1.getText();
                System.out.println("要转换的句子文本为：" + textToSwitch);
                // 这里获取到开始节点与结束节点的信息了，调用获取结果存入result即可
                add_bridge_word.this.result = d.fixText(textToSwitch);
                jTextField1.setText("");

                if (add_bridge_word.this.result != null) {
                    textLabel.setText(add_bridge_word.this.result);
                }
            }
        });
        jPanel_CENTER.add(jButton, BorderLayout.EAST);

        this.add(jPanel_CENTER, BorderLayout.CENTER);
        this.add(jPanel_NORTH, BorderLayout.NORTH);
        this.add(jPanel_SOUTH, BorderLayout.SOUTH);
        jPanel_SOUTH.setPreferredSize(new Dimension(0, 100));
    }

    public void setPanel(JPanel panel) {
        this.setContentPane(panel);
    }

    public String getResult() {
        return this.result;
    }

    public void setTextLabelNull() {
        add_bridge_word.this.textLabel.setText("");
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
}
