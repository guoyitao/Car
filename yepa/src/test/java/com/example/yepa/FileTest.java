package com.example.yepa;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTest {




    public static void main(String[] args)  {

        File file = new File("F:\\code\\学习工程正在做\\log.txt");


        BufferedReader bufferedReader = null;
        Map<String,String> map = new HashMap();
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String temp = null;
            while ((temp = bufferedReader.readLine())!=null){
                if (temp.contains("page")){
                    map.put(
                            StringUtils.substringBefore(temp,"page"),
                            StringUtils.substringAfter(temp,"page/"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            System.out.println(entry.getKey() +"page/"+ entry.getValue());
        }
    }
}
