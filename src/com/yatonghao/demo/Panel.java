package com.yatonghao.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Panel extends JPanel implements KeyListener, ActionListener {
    //声明蛇头和身体图片
    ImageIcon up = new ImageIcon("images/up.png");
    ImageIcon down = new ImageIcon("images/down.png");
    ImageIcon left = new ImageIcon("images/left.png");
    ImageIcon right = new ImageIcon("images/right.png");
    ImageIcon body = new ImageIcon("images/body.png");


    //声明蛇的长度,初始值为3
    int len = 3;
    //声明蛇的X和Y坐标;
    int[] SnakeX = new int[1024];//最大值 = 宽度 * 高度
    int[] SnakeY = new int[1024];

    //枚举类型表示蛇头方向
    Direction direction = Direction.right;

    //标记游戏状态,true为开始游戏
    boolean isStart = false;

    //标志游戏是否结束
    boolean flog = false;

    //创建定时器对象
    Timer timer = new Timer(100, this);

    //声明食物的坐标变量
    int foodX;
    int foodY;

    //声明随机数
    Random random = new Random();

    //声明食物图片
    ImageIcon food = new ImageIcon("images/food.png");

    public Panel() {
        SnakeX[0] = 100;
        SnakeY[0] = 100;
        SnakeX[1] = 75;
        SnakeY[1] = 100;
        SnakeX[2] = 50;
        SnakeY[2] = 100;

        //设置获取焦点为true
        this.setFocusable(true);
        //添加监听
        this.addKeyListener(this);
        //启动定时器
        timer.start();

        //生成食物坐标
        foodX = 25 + 25 * random.nextInt(30);
        foodY = 25 + 25 * random.nextInt(30);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //设置背景颜色
        this.setBackground(Color.red);
        //在画布中添加游戏区域
        g.fillRect(0, 0, 800, 800);

        //添加蛇头和身体图片
        switch (direction) {
            case up:
                up.paintIcon(this, g, SnakeX[0], SnakeY[0]);
                break;
            case down:
                down.paintIcon(this, g, SnakeX[0], SnakeY[0]);
                break;
            case right:
                right.paintIcon(this, g, SnakeX[0], SnakeY[0]);
                break;
            case left:
                left.paintIcon(this, g, SnakeX[0], SnakeY[0]);
                break;
        }
        for (int i = 1; i < len; i++) {
            body.paintIcon(this, g, SnakeX[i], SnakeY[i]);
        }

        //游戏没开始显示提示信息,开始则不显示
        if (!isStart) {
            //添加提示信息
            g.setColor(Color.white);
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.drawString("请按空格键表示游戏开始", 100, 350);
        }
        //设置游戏结束提示信息
        if (flog) {
            g.setColor(Color.red);
            g.setFont(new Font("宋体", Font.BOLD, 50));
            g.drawString("游戏结束", 300, 350);
            g.drawString("按下G键重新开始游戏", 150, 450);
        }

        //添加食物
        food.paintIcon(this, g, foodX, foodY);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!flog) {
            if (keyCode == KeyEvent.VK_SPACE) {//判断空格是否按下
                isStart = !isStart;//按下游戏状态取反
                //重画组件
                repaint();
            } else if (keyCode == KeyEvent.VK_UP) {
                direction = Direction.up;
            } else if (keyCode == KeyEvent.VK_DOWN) {
                direction = Direction.down;
            } else if (keyCode == KeyEvent.VK_LEFT) {
                direction = Direction.left;
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                direction = Direction.right;
            }
        }
        //按下G键重新开始游戏
        if (keyCode == KeyEvent.VK_G) {
            flog = false;
            len = 3;
            SnakeX[0] = 100;
            SnakeY[0] = 100;
            SnakeX[1] = 75;
            SnakeY[1] = 100;
            SnakeX[2] = 50;
            SnakeY[2] = 100;

            foodX = 25 + 25 * random.nextInt(30);
            foodY = 25 + 25 * random.nextInt(30);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (isStart && !flog) {
            //移动身体
            for (int i = len - 1; i > 0; i--) {
                SnakeX[i] = SnakeX[i - 1];
                SnakeY[i] = SnakeY[i - 1];
            }
            switch (direction) {
                case up:
                    SnakeY[0] -= 25;
                    if (SnakeY[0] <= 0) {
                        SnakeY[0] = 800;
                    }
                    break;
                case down:
                    SnakeY[0] += 25;
                    if (SnakeY[0] >= 800) {
                        SnakeY[0] = 0;
                    }
                    break;
                case left:
                    //加入蛇头向右移动,则在当前值+25
                    SnakeX[0] -= 25;

                    //当蛇头的值超过800,则x值变为0
                    if (SnakeX[0] < 0) {
                        SnakeX[0] = 800;
                    }
                    break;
                case right:
                    //加入蛇头向右移动,则在当前值+25
                    SnakeX[0] += 25;

                    //当蛇头的值超过800,则x值变为0
                    if (SnakeX[0] >= 800) {
                        SnakeX[0] = 0;
                    }
                    break;
            }

            //判断蛇头与食物坐标是否一致
            if (SnakeX[0] == foodX && SnakeY[0] == foodY) {
                //蛇的长度加1
                len++;
                //再生成一个新的食物
                foodX = 25 + 25 * random.nextInt(31);
                foodY = 25 + 25 * random.nextInt(31);
            }

            //判断游戏是否结束
            for (int i = 1; i < len - 1; i++) {
                if (SnakeY[0] == SnakeY[i] && SnakeX[0] == SnakeX[i]) {
                    flog = !flog;
                }
            }
            //重新画组件
            repaint();

            //重启定时器
            timer.start();
        }

    }
}
