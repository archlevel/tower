package com.tower.service.generator.manager;

/**
 * Created by kevin on 15/1/4.
 */
public abstract class AbstractLoader implements  ILoader{

    public String genCode(int code,int length){
        String strCode=String.valueOf(code);
        while(strCode.length()<length){
            strCode="0"+strCode;
        }
        return strCode;
    }
    public String genCode(int code){
       return  this.genCode(code,4);
    }

}
