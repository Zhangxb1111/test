package com.test.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fenx1 {

    public static void main(String[] args) throws Exception {
        String filterText = new String();
        System.out.println(getFilterText(filterText));
    }

    public static String getFilterText(String filterText) throws Exception{
        List listWord = new ArrayList();
        FileReader reader = new FileReader("e:/sensitive.txt");
        BufferedReader br = new BufferedReader(reader);

        String s = null;
        while((s = br.readLine())!=null){
            listWord.add(s.trim());
        }
        br.close();
        reader.close();

        Matcher m = null;
            String str1 = new String();
            for (int i=0;i<listWord.size();i++){
                int num = 0;
                Pattern p = Pattern.compile(listWord.get(i).toString(),Pattern.CASE_INSENSITIVE);
                StringBuffer sb = new StringBuffer();
                m = p.matcher(filterText);
                while(m.find()){
                    m.appendReplacement(sb,"*");
                    num++;
                }
                str1 ='\n'+"敏感词 "+p.toString()+" 出现:"+num+"次";
                m.appendTail(sb);
                filterText = sb.toString();
                filterText += str1;
            }
            return filterText;
    }
}
