package com.mapscloud.quickdownload.manager;

import com.mapscloud.quickdownload.task.DownloadTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 9:40
 */
public class ThreadPoolManager {
    private static BlockingQueue<Runnable>  workQueue         = new LinkedBlockingQueue<Runnable>();
    private static RejectedExecutionHandler handler           = new ThreadPoolExecutor.AbortPolicy();
    private static int                      corePoolSize      = 3;
    private static int                      maximumPoolSize   = 5;
    private static int                      keepAliveTime     = 2;
    private static ThreadPoolExecutor       threadPoolExecutor;
    private static ThreadPoolManager        threadPoolManager = new ThreadPoolManager();

    private ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                TimeUnit.HOURS, workQueue, Executors.defaultThreadFactory(), handler);
    }

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }


    public void execute(DownloadTask runnable) {
        threadPoolExecutor.execute(runnable);
    }

    public void remove(DownloadTask runnable) {
        threadPoolExecutor.remove(runnable);
    }


    /**
     * 同时开启下载线程个数
     *
     * @param corePoolSize
     */
    public void setCorePoolSize(int corePoolSize) {
        ThreadPoolManager.corePoolSize = corePoolSize;
    }


}
