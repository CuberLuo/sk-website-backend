package net.sk.webbackend.utils;


import java.io.*;
import java.net.URL;

public class SensitiveWordUtil {
    public static String replace(String s)  {
        String fileUrl="http://1.15.134.164/download/sensitive_words_lines.txt";
        try {
            URL url = new URL(fileUrl);
            InputStream is = url.openStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader buffReader = new BufferedReader(reader);
            String strTmp = "";
            while((strTmp = buffReader.readLine())!=null){
                if(s.contains(strTmp)){
                    StringBuilder star_str= new StringBuilder();
                    for(int i=0;i<strTmp.length();i++){
                        star_str.append("*");
                    }
                    s = s.replace(strTmp,star_str);
                }
            }
            buffReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
}
