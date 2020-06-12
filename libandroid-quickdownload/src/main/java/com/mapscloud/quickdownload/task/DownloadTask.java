package com.mapscloud.quickdownload.task;

import android.util.Log;

import com.mapscloud.quickdownload.bean.Constant;
import com.mapscloud.quickdownload.bean.DownLoadInfo;
import com.mapscloud.quickdownload.https.OkhttpUtils;
import com.mapscloud.quickdownload.listener.OnDownloadEvent;
import com.mapscloud.quickdownload.listener.OnNetRequestListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 10:02
 */
public class DownloadTask implements Runnable, OnNetRequestListener {


    private final DownLoadInfo    downLoadInfo;
    private final OnDownloadEvent onDownloadEvent;

    public DownloadTask(DownLoadInfo downLoadInfo, OnDownloadEvent onDownloadEvent) {
        this.downLoadInfo = downLoadInfo;
        this.onDownloadEvent = onDownloadEvent;
        if (onDownloadEvent != null)
            onDownloadEvent.stateChange(Constant.STATE_WAITING, downLoadInfo);
    }

    @Override
    public void run() {
        OkhttpUtils.getInstance().setOnNetRequestListener(this);
        OkhttpUtils.getInstance().netRequest(downLoadInfo.url);
    }

    @Override
    public void onFailure(String error) {
        if (onDownloadEvent != null)
            onDownloadEvent.stateChange(Constant.STATE_NETERROR, downLoadInfo);
    }

    @Override
    public void onResponse(Response response) {
        ResponseBody body = response.body();
        if (body == null)
            return;
        if (onDownloadEvent != null)
            onDownloadEvent.stateChange(Constant.STATE_START, downLoadInfo);
        long contentLength = body.contentLength();
        downLoadInfo.setSize(contentLength);
        File file = new File(downLoadInfo.path);
        if (!file.exists())
            file.mkdir();
        InputStream inputStream = body.byteStream();
        int len = 0;
        int progress = 0;
        byte[] bytes = new byte[1024];
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(downLoadInfo.path + "/" + downLoadInfo.name);
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
                progress += len;
                Log.e("wyc", "progress：" + progress +"---"+ "progress * 100：" + progress * 100 +"---总大小:"+  downLoadInfo.size);
                final int percent = (int) (progress * 100 / downLoadInfo.size);
                fileOutputStream.flush();
                downLoadInfo.setPercent(percent);
                if (onDownloadEvent != null)
                    onDownloadEvent.stateChange(Constant.STATE_DOWNLOADING, downLoadInfo);
            }
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (onDownloadEvent != null)
                onDownloadEvent.stateChange(Constant.STATE_ERROR, onDownloadEvent);
        }
        if (onDownloadEvent != null)
            onDownloadEvent.stateChange(Constant.STATE_FINISH, downLoadInfo);

    }
}
