package com.mapscloud.quickdownload.listener;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 10:29
 */
public interface OnDownloadEvent {

    void stateChange(int state, Object obj);

}
