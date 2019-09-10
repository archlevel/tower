package com.tower.service.job;

import java.util.List;

public interface INormalJob<T> extends IJob<T>{
    /**
     * 实现job的执行 eg:调用service获取数据
     */
    public List<T> execute();

}
