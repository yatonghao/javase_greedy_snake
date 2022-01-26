package com.yatonghao.demo;

import javax.swing.*;

public class Snake {
    public static void main(String[] args) {
        //创建窗口
        JFrame frame = new JFrame();
        //设置窗口大小
        frame.setBounds(600, 50, 800, 800);
        //不允许推拽改变窗口大小
        frame.setResizable(false);
        //点击窗口关闭按钮退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //添加画布
        frame.add(new Panel());
        //显示窗口
        frame.setVisible(true);
    }
}
