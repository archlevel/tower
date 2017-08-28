package com.tower.service.concurrent;

import java.util.Map;

import com.tower.service.exception.basic.BasicException;
import com.tower.service.log.LogUtils;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

/**
 * 和SynMAsynBizExecutor配套使用，主要是解决让多个同步操作【变成异步同时操作】；提过系统性能；
 * 原来有个方法A，需要a，b，c，d个步骤；其中每个步骤分别是10ms,20ms,5ms,30ms;按照线性执行的化则需要花费10+20+5+30=65ms;
 * 通过这个算法可以max(a,b,c,d)+线程却换时间片<65ms;
 * 
 * @author evans
 *
 */
public class SynMAsynBizHelper {

    protected static Logger _logger = LoggerFactory.getLogger("trace");

    public static void errorCheck(long start, Map<Integer, Object> map) {
        errorCheck("SynMAsynBizExecutor", start, map);
    }

    public static void errorCheck(String bizMethod, long start, Map<Integer, Object> map) {
        Integer size = (Integer) map.get(0);
        if (size != null) {
            int tmpSize = -1;
            try {
                finishCheck(map);
                tmpSize = map.size() - 1;
                for (int i = 1; i < tmpSize + 1; i++) {
                    Object resultRef = map.get(i);
                    if (resultRef instanceof BasicException) {
                        throw (BasicException) resultRef;
                    } else if (resultRef instanceof RuntimeException) {
                        throw (RuntimeException) resultRef;
                    } else if (resultRef instanceof Exception) {
                        throw new RuntimeException((Exception) resultRef);
                    }
                }
            } finally {
                LogUtils.timeused(_logger, "SynMAsynBizExecutor.execute(" + bizMethod + ")", start);
            }
        }
    }

    public static void finishCheck(Map<Integer, Object> map) {
        Integer size = (Integer) map.get(0);
        if (size != null) {
            synchronized (map) {
                while (true) {
                    if ((map.size() - 1) < size) {
                        try {
                            map.wait();
                        } catch (InterruptedException e) {
                            
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }
}
