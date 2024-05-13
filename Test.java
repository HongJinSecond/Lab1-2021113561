/*
 * @Descripttion: 
 * @version: 
 * @Author: 陈左维2021113561
 * @email: 756547077@qq.com
 * @Date: 2024-05-13 18:29:30
 * @LastEditors: 陈左维2021113561
 * @LastEditTime: 2024-05-13 18:29:58
 */

import javax.swing.JButton;
import java.awt.event.*;
/**
 * Test
 */
public class Test {

    public void test() {
        //这是返回按钮，不用调用后台方法就能用的，你可以初始化这些GUI事件
        JButton retunButton = new JButton("返回");
        retunButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
				//在这里实现GUI跳转功能
			}
        });
        //这是调用后台生成图片的按钮
        JButton readJPGButton = new JButton();
        //这是图片路径，暂时空着
        String path = "";
        readJPGButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                //在这里实现调用后台方法的功能
                //path=g.readGraph()
			}
        });
    }
}