package com.tower.service.exception.manager.model;

import com.tower.service.exception.basic.ExceptionLevel;
import com.tower.service.exception.basic.ExceptionType;

/**
 * Created by kevin on 15/1/6.
 */
public class KjtException {
    private int id;
    private int code;
    private int type;
    private String message;
    private int spid;
    private int level;
    private ExceptionType exceptionType;
    private KjtSoaSp ajkSoaSp;
    private ExceptionLevel exceptionLevel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        for(ExceptionType et:ExceptionType.values()){
            if(et.getValue()==type){
                exceptionType=et;
            }
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    /**
     * type sp level code
     * @return
     */
    public String getFullCode(){
        return  type+"["+genCode(this.spid,2)+"|spId]"+this.level+genCode(this.code,4);
    }
    public String getEnumKey(){
        return  "MSG_"+this.getType()+"_"+genCode(this.code,4);
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    private String genCode(int c,int length){
        String _c=String.valueOf(c);
        while(_c.length()<length){
            _c="0"+_c;
        }
        return _c;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        for(ExceptionLevel l:ExceptionLevel.values()){
            if(l.getValue()==level){
                exceptionLevel=l;
            }
        }
    }

    public KjtSoaSp getAjkSoaSp() {
        return ajkSoaSp;
    }

    public void setAjkSoaSp(KjtSoaSp ajkSoaSp) {
        this.ajkSoaSp = ajkSoaSp;
    }

    public ExceptionLevel getExceptionLevel() {
        return exceptionLevel;
    }
}
