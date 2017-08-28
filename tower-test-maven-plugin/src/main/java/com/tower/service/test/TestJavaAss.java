package com.tower.service.test;

import java.lang.reflect.Method;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class TestJavaAss {

    
    public static void main(String[] args) {
        Paranamer paranamer = new BytecodeReadingParanamer(); 
        Class clazz = AA.class;
        try {
            Method[] cms = clazz.getDeclaredMethods();
            for(int m=0;m<cms.length;m++){
                String[] parameterNames = paranamer.lookupParameterNames(cms[m]);
                int size = parameterNames==null?0:parameterNames.length;
                for(int i=0;i<size;i++){
                    System.out.println(parameterNames[i]);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
