package com.tower.service.exception.basic;

/**
 * 异常类型定义.
 */
public enum ExceptionType {
    RUNTIME_EXCEPTION(1,"运行时异常"),
    PARAM_EXCEPTION(2,"参数不合法异常"),
    LOGIC_EXCEPTION(3,"业务逻辑异常");

    private int value;
    private String name;
    private  ExceptionType(int value,String name){
        this.value=value;
        this.name=name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}
