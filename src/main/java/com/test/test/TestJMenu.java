package com.test.test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;

public class TestJMenu extends JFrame implements ActionListener {

    JTextArea jta = new JTextArea();

    TestJMenu(){
        this.setSize(400,300);
        JMenuBar jmb = new JMenuBar();
        this.setTitle("分析程序");
        //创建菜单栏
        JMenu jmFile = new JMenu("文件");
        JMenuItem jmiNew = new JMenuItem("新建");
        JMenuItem jmiOpen = new JMenuItem("打开");
        jmiOpen.addActionListener(this);
        jmFile.add(jmiNew);
        jmFile.add(jmiOpen);

        JMenu jmFenxi = new JMenu("分析");
        JMenuItem jmiSure = new JMenuItem("确定");
        JMenuItem jmiCancel = new JMenuItem("取消");
        jmFenxi.add(jmiSure);
        jmFenxi.add(jmiCancel);

        JMenu jmHelp = new JMenu("帮助");

        jmb.add(jmFile);
        jmb.add(jmFenxi);
        jmb.add(jmHelp);
        this.setJMenuBar(jmb);
        this.getContentPane().add(jta);
        this.setVisible(true);
        //设置事件监听,如果点击了分析中的确定，表名要进行分析测试
        jmiSure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filterText = jta.getText();
                try {
                    jta.setText(Fenx.getFilterText(filterText));
                } catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });
    }

    //对打开按钮进行事件监听
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jc = new JFileChooser();
        if (jc.showOpenDialog(this) != 1){
            try{
                File file = jc.getSelectedFile();
                FileInputStream fis = new FileInputStream(file);
                byte[] buf = new byte[10*1024];
                int len = fis.read(buf);
                jta.append(new String(buf,0,len));
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        MinGanCiHui m = new MinGanCiHui();
        TestJMenu jmFrame = new TestJMenu();
    }
}
