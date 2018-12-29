package com.test.test;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Shows {
    static JTextArea ss;
    JFrame j1;

    Shows(){
        ss = new JTextArea();
        j1 = new JFrame("敏感词汇文本显示窗口");
        String ss1 = filein();
        j1.add(ss);
        j1.setSize(500,400);
        j1.setVisible(true);
    }
    public static String filein(){
        String f = "e:/sensitive.txt";
        String str = "";
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            while((str = br.readLine()) != null){
                i++;
                str = str+"\n";
                ss.append(str);
            }
        }catch (Exception e){
            e.getStackTrace();
        }
        return str;
    }
}
