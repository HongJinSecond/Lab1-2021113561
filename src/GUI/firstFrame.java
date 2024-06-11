package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 这是对应需求的登陆界面
 */
public class firstFrame extends JFrame {
    JFrame MainFrame;
    public String text_path;
    public JButton jButton;

    public firstFrame() {
        super("firstFrame");

        // 设置字体
        Font font = new Font("Microsoft YaHei", Font.BOLD, 24);

        JPanel jPanel_CENTER = new JPanel();
        JPanel jPanel_SOUTH = new JPanel();
        JPanel jPanel_NORTH = new JPanel();
        jPanel_CENTER.setLayout(new BorderLayout());
        jPanel_SOUTH.setLayout(new BorderLayout());
        jPanel_NORTH.setLayout(new BorderLayout());

        this.setSize(1200, 900);
        this.setLayout(new BorderLayout());

        // 选择文件的按钮
        JButton choosePathButton = new JButton("choose text path");
        choosePathButton.setFont(font);
        choosePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                // 设置默认当前目录为文件系统的用户目录（可选项）
                fileChooser.setCurrentDirectory(new java.io.File("."));
                // 显示文件选择对话框
                int returnValue = fileChooser.showOpenDialog(firstFrame.this);

                // 如果用户选择了一个文件（点击了打开）
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    firstFrame.this.text_path = selectedFile.getPath();
                } else {
                    // 用户取消了选择或关闭了对话框
                }
            }
        });

        // 跳转到主页的按钮
        jButton = new JButton("Jump to mainFrame");
        jButton.setFont(font);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            BufferedImage originalImage = ImageIO.read(new File("src/pic/logo.png"));

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

        // 稍微调整一下布局
        jPanel_SOUTH.add(choosePathButton, BorderLayout.WEST);
        jPanel_SOUTH.add(jButton, BorderLayout.EAST);
        this.add(jPanel_SOUTH, BorderLayout.SOUTH);
        this.add(jPanel_CENTER, BorderLayout.CENTER);

    }

    public String getText_path() {
        return this.text_path;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.MainFrame = mainFrame;
    }

}
