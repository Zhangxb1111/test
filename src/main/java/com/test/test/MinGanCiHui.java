package com.test.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinGanCiHui {

    public static void main(String args[]){
        JFrame fl = new JFrame("敏感词分析程序");
        fl.setBounds(100,100,500,400);
        fl.setBackground(Color.lightGray);
        fl.setLayout(new GridLayout());
        Panel pl = new Panel();
        fl.add(pl);

        Panel p2 = new Panel();
        fl.add(p2);
        fl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel l2 = new JLabel("敏感词汇保存在sensitive.txt文件中,查看敏感词汇请点击：");
        pl.add(l2);
        JButton b1 = new JButton("我要查看");
        pl.add(b1);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Shows s = new Shows();
            }
        });

        JLabel l1 = new JLabel("请输入含有敏感词的文本文件,点确定进入:");
        p2.add(l1);
        JButton b2 = new JButton("确定");
        p2.add(b2);
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TestJMenu jmFrame = new TestJMenu();
            }
        });
        fl.setVisible(true);
    }
}
