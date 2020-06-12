package com.mapscloud.quickdownload.bean;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 10:35
 */
public class Constant {

    public static final int STATE_NONE        = 0;//未下载
    public static final int STATE_WAITING     = 1;//等待中，任务创建并添加到线程池，但是run方法没有执行
    public static final int STATE_START       = 2;// 开始下载
    public static final int STATE_DOWNLOADING = 3;//下载中
    public static final int STATE_PAUSE       = 4;//暂停
    public static final int STATE_FINISH      = 5;//下载完成
    public static final int STATE_ERROR       = 6;//下载出错
    public static final int STATE_NETERROR    = 7;//网络错误

}