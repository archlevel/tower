package com.tower.service.job;

public class JobExecuteException extends JobException{
    public JobExecuteException(Throwable ex){
        super("job execute exception @ ",ex);
    }
}
