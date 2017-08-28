package com.tower.service.concurrent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.tower.service.exception.basic.BasicException;
import com.tower.service.log.LogUtils;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.Request;

/**
 * 分页并发执行器
 * 
 * @author evans
 *
 */
public abstract class BSynMAsynBizExecutor<T> {

    protected Logger logger = LoggerFactory.getLogger("trace");
    private static int processNum = Runtime.getRuntime().availableProcessors();
    private List<T> results = new CopyOnWriteArrayList<T>();
    private String reqId = (String) Request.getId();

    /**
     * 分页并发执行器 默认5个并发线程处理
     * 
     * @param bizMethod 业务方法
     * @param datas 批量待处理数据
     */
    public BSynMAsynBizExecutor(String bizMethod, Set<T> datas) {
        this(bizMethod, processNum, datas);
    }

    /**
     * 分页并发执行器 默认5个并发线程处理
     * 
     * @param bizMethod 业务方法
     * @param datas 批量待处理数据
     */
    public BSynMAsynBizExecutor(String bizMethod, List<T> datas) {
        this(bizMethod, processNum, datas);
    }

    /**
     * 分页并发执行器
     * 
     * @param bizMethod 业务方法
     * @param batchSize 分页大小
     * @param datas 批量待处理数据
     */
    public BSynMAsynBizExecutor(String bizMethod, int batchSize, List<T> datas) {
        int total = datas == null ? 0 : datas.size();
        int page = getPages(total, batchSize);
        Iterator<T> its = datas.iterator();
        doExceute(bizMethod, total, page, batchSize, its);
    }

    /**
     * 分页并发执行器
     * 
     * @param bizMethod 业务方法
     * @param batchSize 分页大小
     * @param datas 批量待处理数据
     */
    public BSynMAsynBizExecutor(String bizMethod, int batchSize, Set<T> datas) {
        int total = datas == null ? 0 : datas.size();
        int page = getPages(total, batchSize);
        Iterator<T> its = datas.iterator();
        doExceute(bizMethod, total, page, batchSize, its);
    }

    private int getPages(int total, int pageSize) {
        if (total > 0) {
            int mod = total % pageSize;
            int page = total / pageSize;
            if (mod > 0) {
                page = page + 1;
            }
            return page;
        }
        return 0;
    }

    private final void doExceute(String bizMethod, int totalSize, int page, int batchSize,
            Iterator<T> its) {
        int tmp = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < page; i++) {
            ConcurrentHashMap<Integer, Object> map = new ConcurrentHashMap<Integer, Object>();
            for (int j = 0; j < batchSize; j++) {
                int idx = j + 1;
                final T single = its.next();
                tmp++;
                new SynMAsynBizExecutor(map, bizMethod, idx) {
                    @Override
                    public void execute() {
                        try {
                            executes(single);
                            _logger.info(reqId + "success");
                        } catch (Exception ex) {
                            _logger.info(reqId + ex.getMessage());
                            onErrors(single, new RuntimeException(ex));
                        }
                    }
                };

                if (tmp == totalSize) {
                    break;
                }
            }
            SynMAsynBizHelper.finishCheck(map);
        }
        LogUtils.timeused(logger, "BSynMAsynBizExecutor.doExceute(" + bizMethod + ") ", start);
    }

    /**
     * 业务处理
     * 
     * @param object 处理对象
     */
    public abstract void executes(T object);

    protected void onErrors(T object, RuntimeException ex) throws RuntimeException {
        if (ex instanceof BasicException) {
            throw (BasicException) ex;
        } else {
            throw ex;
        }
    }

    public final void addResult(T result) {
        results.add(result);
    }

    public List<T> getResults() {
        return results;
    }

    public static void main(String[] args) {


        List<Integer> datas = new ArrayList<Integer>();
        datas.add(1);
        datas.add(2);
        datas.add(3);
        datas.add(4);
        datas.add(5);
        datas.add(6);
        datas.add(7);

        new BSynMAsynBizExecutor<Integer>("test", 5, datas) {
            @Override
            public void executes(Integer object) {
                try {
                    long time = Double.valueOf(Math.random() * 1000).longValue();
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    logger.error(e);
                }
            }
        };

    }
}
