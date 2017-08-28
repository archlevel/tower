package com.tower.service.exception.basic;

/**
 * 异常层级
 */
public enum ExceptionLevel {
	RPC_ACCESS(4,"远程服务调用层"),
    DATA_ACCESS(3,"数据访问层"),
    CONTROLLER(1,"控制层"),
    SERVICE(2,"业务层"),
    NORMAL(0,"通用");
    private int value;
    private String name;
    private ExceptionLevel(int value,String name){
        this.value=value;
        this.name=name;
    }
    public int getValue(){
        return this.value;
    }
    public String getName(){
        return this.name;
    }

}
