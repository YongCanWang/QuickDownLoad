package com.mapscloud.quickdownload.manager;

import com.mapscloud.quickdownload.bean.DownLoadInfo;
import com.mapscloud.quickdownload.listener.OnDownloadEvent;
import com.mapscloud.quickdownload.task.DownloadTask;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 10:22
 */
public class QuickDownLoadManager {
    private static QuickDownLoadManager quickDownLoadManager = new QuickDownLoadManager();


    private QuickDownLoadManager() {
    }

    public static QuickDownLoadManager getInstance() {
        return quickDownLoadManager;
    }


    public void downLoadFile(DownLoadInfo downLoadInfo, OnDownloadEvent onDownloadEvent) {
        DownloadTask downloadTask = new DownloadTask(downLoadInfo, onDownloadEvent);
        ThreadPoolManager.getInstance().execute(downloadTask);
    }


    public void setWorkPoolMax(int max) {
        ThreadPoolManager.getInstance().setCorePoolSize(max);
    }



    public static class State {
        public static final int STATE_NONE        = 0;//未下载
        public static final int STATE_WAITING     = 1;//等待中，任务创建并添加到线程池，但是run方法没有执行
        public static final int STATE_START       = 2;// 开始下载
        public static final int STATE_DOWNLOADING = 3;//下载中
        public static final int STATE_PAUSE       = 4;//暂停
        public static final int STATE_FINISH      = 5;//下载完成
        public static final int STATE_ERROR       = 6;//下载出错
        public static final int STATE_NETERROR    = 7;//网络错误
    }


}
