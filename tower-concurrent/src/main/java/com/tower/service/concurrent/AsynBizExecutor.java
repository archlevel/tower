package com.tower.service.concurrent;



import com.tower.service.log.LogUtils;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.Request;

/**
 * 高性能控制器
 * 异步执行器
 * 
 * @author alex.zhu
 *
 */
public abstract class AsynBizExecutor implements Runnable {

    protected Logger logger = LoggerFactory.getLogger("trace");
    private String reqId = (String) Request.getId();
    private String biz = "";

    public AsynBizExecutor(String biz) {
        this.biz = biz;
        start();
    }

    private void start() {
        Executor.execute(this);
    }

    @Override
    public void run() {
        final long start = System.currentTimeMillis();
        Request.setId(this.getReqId());
        try {
            execute();
        } catch (Exception ex) {
            LogUtils.error(logger, ex);
            onErrors(new RuntimeException(ex));
        } finally {
            LogUtils.timeused(logger, "AsynBizExecutor.execute(" + biz + ")", start);
        }
    }

    public abstract void execute();

    public void onErrors(RuntimeException ex) {

    }

    public String getReqId() {
        return reqId;
    }

}
