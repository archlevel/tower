package com.tower.service.job;

public class PageLoadException extends JobException{
    public PageLoadException(Throwable ex){
        super("page load exception",ex);
    }
}
