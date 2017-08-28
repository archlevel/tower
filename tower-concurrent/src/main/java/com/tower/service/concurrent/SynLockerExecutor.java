package com.tower.service.concurrent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.KeeperException;

import com.tower.service.config.DynamicZookeeper;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.util.DateUtil;
import com.tower.service.util.Request;

/**
 * 并发处理协调器
 * 
 * @author alex
 * 
 */
public abstract class SynLockerExecutor {

    protected static Logger logger = LoggerFactory.getLogger(SynLockerExecutor.class);

    private static CuratorFramework client;

    private static DynamicZookeeper loader = new DynamicZookeeper();

    private String reqId = Request.getId();

    private InterProcessMutex lock = null;

    private boolean tryUntilSuccessed = true;// try 50 times

    /**
     * 
     * @param cls 调用者类 不能为空
     * @param method 调用所在的方法名 不能为空
     * @param element 待锁定的对象（一般是对象的pk值） 不能为空
     * @param tryUntilSuccessed 当为false时，只try一次，当为true时表示直到成功获取到锁退出
     */
    public SynLockerExecutor(Class cls, String method, Object element, boolean tryUntilSuccessed) {
        this(cls, method, element, 1, tryUntilSuccessed);
    }

    /**
     * 
     * @param cls
     * @param method
     * @param element
     * @param lockTime [db lockTime] 单位秒
     * @param tryUntilSuccessed 当为false时，只try一次，当为true时表示直到成功获取到锁退出
     */
    public SynLockerExecutor(Class cls, String method, Object element, Integer lockTime,
            boolean tryUntilSuccessed) {
        Request.setId(reqId);
        this.tryUntilSuccessed = tryUntilSuccessed;
        StringBuffer holder = new StringBuffer();
        holder.append(cls.getName());
        holder.append(".");
        holder.append(method);
        holder.append("(");
        holder.append(element);
        holder.append(")");

        String key = holder.toString();

        long start = System.currentTimeMillis();
        try {
            lock(key, lockTime);
            long executeStart = System.currentTimeMillis();
            execute();
        } catch (Exception e) {
            element = null;
            throw new RuntimeException(e);
        } finally {
            try {
                unlock(key);
            } catch (Exception ex) {}
        }
    }

    /**
     * 
     * @param element
     * @param lockTime
     */
    protected void lock(Object element, Integer lockTime) {
        long start = System.currentTimeMillis();
        String key = element.toString();
        try {
            zookeeperLocker(key, loader.getLockAcquireTimeout());

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {}
    }

    protected void unlock(Object element) {

        String key = element.toString();

        zookeeperUnlock(key);
    }

    /**
     * 连接zookeeper server
     */
    private void conn() {

        synchronized (SynLockerExecutor.class) {
            if (client == null) {
                long start_ = System.currentTimeMillis();

                client = loader.getClient();
            }
        }
    }

    /**
     * zookeeper 锁实现
     * 
     * @param key
     * @param lockAcquireTimeout
     */
    protected void zookeeperLocker(String key, int lockAcquireTimeout) {

        long start = System.currentTimeMillis();

        conn();

        if (key.indexOf("/") != 0) {
            key = "/" + key;
        }

        start = System.currentTimeMillis();

        lock = new InterProcessMutex(client, key);

        long start_ = start;
        int getLockFailedCount = 0;
        while (true) {
            try {

                getLockFailedCount++;
                if (!lock.acquire(lockAcquireTimeout, TimeUnit.MILLISECONDS)) {
                    start_ = System.currentTimeMillis();
                    failedCheck(getLockFailedCount);
                    continue;
                }

                /**
                 * 设置最后访问时间 为housekeeping做准备
                 */
                client.setData().forPath(key,
                        String.valueOf(DateUtil.getUnixTimestamp()).getBytes());
                break;
            } catch (KeeperException.ConnectionLossException ex) {
                client = null;
            } catch (KeeperException.SessionExpiredException ex) {
                client = null;
            } catch (KeeperException.SessionMovedException ex) {
                client = null;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    private void zookeeperUnlock(String key) {

        long start = System.currentTimeMillis();

        if (key.indexOf("/") != 0) {
            key = "/" + key;
        }

        try {
            if (lock != null) {
                lock.release();
                lock = null;
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {}
    }

    private void failedCheck(int getLockFailedCount) {
        if (!tryUntilSuccessed) {
            int cnt = 3;
            if (getLockFailedCount > cnt) {
                throw new RuntimeException(" with 超时");
            }
        }
    }

    public abstract void execute();

    private static class HouseKeeper extends Thread {
        @Override
        public void run() {
            InterProcessMutex lock = null;
            while (true) {
                try {
                    synchronized (SynLockerExecutor.class) {
                        if (client == null || client.getState() != CuratorFrameworkState.STARTED) {
                            Thread.currentThread().sleep(1000);
                            continue;
                        }
                        if (lock == null) {
                            lock = new InterProcessMutex(client, "/" + HouseKeeper.class);
                        }
                    }
                    while (true) {
                        if (!lock.acquire(loader.getLockAcquireTimeout(), TimeUnit.MILLISECONDS)) {
                            Thread.sleep(86400);
                            continue;
                        }
                        break;
                    }

                    List<String> childs = client.getChildren().forPath("/");
                    for (int i = 0; i < childs.size(); i++) {
                        check(childs.get(i));
                    }
                } catch (Exception e) {}
            }
        }

        private void check(String path) throws Exception {
            if (path.indexOf("/") != 0) {
                path = "/" + path;
            }
            List<String> childs = client.getChildren().forPath(path);
            if (childs.size() > 0) {
                for (int i = 0; i < childs.size(); i++) {
                    check(path + "/" + childs.get(i));
                }
            } else {
                String lastime = new String(client.getData().forPath(path));
                if (lastime != null && lastime.trim().length() > 0) {
                    if (Integer.valueOf(lastime) < DateUtil.getUnixTimestamp() - 86400) {
                        client.delete().forPath(path);
                    }
                }
            }
        }
    }

    public static void test() {
        Thread t = new Thread() {
            public void run() {
                int idx = 0;
                while (true) {
                    Request.setId(Thread.currentThread().getName() + "@" + (++idx));
                    try {
                        new SynLockerExecutor(SynLockerExecutor.class, "test0", 1, true) {
                            @Override
                            public void execute() {
                                try {
                                    System.err.println(Request.getId() + " before " + "@"
                                            + System.currentTimeMillis());
                                    long t = Double.valueOf(Math.random() * 100).longValue();
                                    Thread.currentThread().sleep(t);
                                    System.err.println(Request.getId() + " exec used: " + t
                                            + " after " + "@" + System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    logger.error(e);
                                }
                            }
                        };
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            }
        };
        t.start();
        t = new Thread() {
            public void run() {
                int idx = 0;
                while (true) {
                    Request.setId(Thread.currentThread().getName() + "@" + (++idx));
                    try {
                        new SynLockerExecutor(SynLockerExecutor.class, "test0", 1, true) {
                            @Override
                            public void execute() {
                                try {
                                    System.err.println(Request.getId() + " before " + "@"
                                            + System.currentTimeMillis());
                                    long t = Double.valueOf(Math.random() * 100).longValue();
                                    Thread.currentThread().sleep(t);
                                    System.err.println(Request.getId() + " exec used: " + t
                                            + " after " + "@" + System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    logger.error(e);
                                }
                            }
                        };
                    } catch (Exception ex) {
                        logger.error(ex);
                    }
                }
            }
        };

        t.start();
        while (true) {
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }

    private static void list(String sub) {

        CuratorFramework client = loader.getClient();

        try {
            List<String> childs = client.getChildren().forPath("/");

            for (int i = 0; i < childs.size(); i++) {
                if (childs.get(i).indexOf(sub) != -1) {
                    client.getData().forPath("/" + childs.get(i));
                    // client.delete().forPath("/"+childs.get(i));
                }
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    public static void main(String[] args) {
        // list("xstream");
        test();
    }

}
