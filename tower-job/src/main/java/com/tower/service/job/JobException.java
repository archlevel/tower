package com.tower.service.job;

public class JobException extends RuntimeException {
    public JobException(String message,Throwable ex){
        super(message,ex);
    }
}
