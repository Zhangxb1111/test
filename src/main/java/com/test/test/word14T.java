package com.test.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class word14T {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File file=new File("word.txt");
        //File file1=new File("wrong.txt");
        TestWord test=new TestWord();
        test.setFile(file);//类调用setfile方法
        test.setStopTime(5);//设置中间的暂停时间
        test.startTest();
    }
}
class TestWord
{
    File file;
    int stopTime;
    public void setFile(File f)
    {
        file=f;
    }
    public void setStopTime(int t)
    {
        stopTime=t;
    }
    public void startTest()
    {
        Scanner sc=null;

        Scanner read=new Scanner(System.in);
        try
        {


            sc=new Scanner(file);
            while(sc.hasNext())
            {

                String word=sc.next();
                System.out.println(word);//显示要背的单词
                System.out.println("给"+stopTime+"秒背单词的时间");
                Thread.sleep(stopTime*1000);//使程序休眠一段时间来背诵单词
                System.out.println("\r");
                for(int i=1;i<=10;i++)
                {
                    System.out.println("*");
                }//将前面的内容覆盖
                System.out.println("输入曾经显示的单词");
                String input=read.nextLine();//将单词读入input中
                if(input.equals(word))
                {
                    System.out.println("单词正确");//匹配两个单词是否相同
                }
                else
                {
                    while(true)//将背错的程序放在死循环中，如果正确则跳出
                    {   System.out.println("单词背错了，继续背该单词");
                        System.out.println(word);
                        FileWriter intwo=new FileWriter("wrong.txt");
                        //BufferedWriter tofile=new BufferedWriter(intwo);
                        BufferedWriter out=new BufferedWriter(intwo);
                        out.write(word);
                        System.out.println("给"+stopTime+"秒背单词的时间");
                        Thread.sleep(stopTime*1000);
                        System.out.println("\r");
                        for(int i=1;i<=10;i++)
                        {
                            System.out.println("*");
                        }
                        System.out.println("输入曾经显示的单词");
                        input=read.nextLine();
                        if(input.equals(word))
                        {
                            System.out.println("单词正确");
                            break;
                        }
                    }
                }
            }
        }
        catch(Exception exp)
        {
            System.out.println(exp);
        }
    }
}
