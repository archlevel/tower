package com.tower.service.domain;


import java.util.ArrayList;
import java.util.List;

public class App
{
    private List<Integer> p = new ArrayList<Integer>();

    private static int num = 0;

    private List<Data> get(int i,String index){

        p.add(0, new Integer(num++));

        Data data1 = new Data();
        data1.map.put("key1", i+1);
        data1.map.put("key2",i+2);
        data1.age = 10;
        data1.name = "liyao";

        Data data2 = new Data();
        data2.map.put("key3",i+3);

        List<Data> data = new ArrayList<Data>();
        data.add(data1);
        data.add(data2);

        return data;
    }

    private void run(){
        for (int i = 0; i < 1000000000; i++) {
            try {
                Thread.sleep(5000);
                System.out.println(get(i,"index:"+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main( String[] args ) {

        new App().run();
    }
}