package com.mapscloud.quickdownload.listener;

import okhttp3.Response;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 9:26
 */
public interface OnNetRequestListener {
    void onFailure(String error);
    void onResponse(Response response);
}
