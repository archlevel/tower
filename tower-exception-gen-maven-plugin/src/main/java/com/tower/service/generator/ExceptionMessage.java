package com.tower.service.generator;

/**
 * 异常信息
 */
public class ExceptionMessage implements Comparable<ExceptionMessage>{

    private int id;
    private int code;
    private int type;
    private String message;
    private int spid;
    private int level;

    public ExceptionMessage(){}
    public ExceptionMessage(int code,String message){
        this.code=code;
        this.message=message;
    }
    public ExceptionMessage(int code,String message,int type,int level,int spid){
        this.code=code;
        this.message=message;
        this.type=type;
        this.spid=spid;
        this.level=level;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int typeId) {
        this.type = type;
    }

    public String getFullCode(){
        return  type+"{spid}"+this.level+genCode(this.code,Constant.CODE_LENGTH);
    }

    /**
     * 编码 不足长度补0
     * @param c
     * @param length
     * @return
     */
    public static  String genCode(int c,int length){
        String _c=String.valueOf(c);
        while(_c.length()<length){
            _c="0"+_c;
        }
        return _c;
    }

    public int getSpid() {
        return spid;
    }

    public void setSpid(int spid) {
        this.spid = spid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int compareTo(ExceptionMessage o) {
        return this.code==o.getCode()?1:0;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getCode()==((ExceptionMessage) obj).getCode());
    }

}
