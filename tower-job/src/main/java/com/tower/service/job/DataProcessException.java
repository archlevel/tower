package com.tower.service.job;

public class DataProcessException extends JobException{
    public DataProcessException(Throwable ex){
        super("data process exception",ex);
    }
}
