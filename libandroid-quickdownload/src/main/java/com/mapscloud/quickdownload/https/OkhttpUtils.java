package com.mapscloud.quickdownload.https;

import com.mapscloud.quickdownload.listener.OnNetRequestListener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 9:01
 */
public class OkhttpUtils {

    private static OkhttpUtils          okhttpUtils = new OkhttpUtils();
    private        OnNetRequestListener onNetRequestListener;

    private OkhttpUtils() {
    }

    public static OkhttpUtils getInstance() {
        return okhttpUtils;
    }


    public void netRequest(String url) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder().get()
                .url(url)
                .build();


        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (onNetRequestListener != null)
                    onNetRequestListener.onFailure(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (onNetRequestListener != null)
                    onNetRequestListener.onResponse(response);
            }
        });
    }


    public void setOnNetRequestListener(OnNetRequestListener onNetRequestListener) {
        this.onNetRequestListener = onNetRequestListener;
    }
}
