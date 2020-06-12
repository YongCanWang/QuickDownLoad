package com.mapscloud.quickdownload;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.mapscloud.quickdownload.bean.DownLoadInfo;
import com.mapscloud.quickdownload.listener.OnDownloadEvent;
import com.mapscloud.quickdownload.manager.QuickDownLoadManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private              String   TAG                        = MainActivity.this.getClass().getSimpleName();
    private              String   PATH                       = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mapPioneer";
    private              boolean  mShowRequestPermission     = true;
    private static final String   COARSE_LOCATION_PERMISSION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final String   FINE_LOCATION_PERMISSION   = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String   WRITE_STORAGE_PERMISSION   = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String   READ_STORAGE_PERMISSION    = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static       String[] ALL_APP_PERMISSIONS        = new String[]{
            WRITE_STORAGE_PERMISSION,
            READ_STORAGE_PERMISSION,
            Manifest.permission.READ_PHONE_STATE,
            FINE_LOCATION_PERMISSION,
            COARSE_LOCATION_PERMISSION,
            Manifest.permission.READ_CONTACTS,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_permission();


        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://119.3.195.167:81/offline_data/0beijing.zip";
                String name = "基础数据";
                long size = 32191841;
                String localPath = "/base.zip";


                DownLoadInfo downLoadInfo = new DownLoadInfo();
                downLoadInfo.setID(url);
                downLoadInfo.setUrl(url);
                downLoadInfo.setPath(PATH);
                downLoadInfo.setName(localPath);
                downLoadInfo.setSize(size);
                QuickDownLoadManager.getInstance().downLoadFile(downLoadInfo, new OnDownloadEvent() {
                    @Override
                    public void stateChange(int state, Object obj) {
                        if (obj != null && obj instanceof DownLoadInfo) {
                            DownLoadInfo downLoadInfo = (DownLoadInfo) obj;
                            switch (state) {
                                case QuickDownLoadManager.State.STATE_NONE: // 未下载
                                    Log.i(TAG, "未下载:" + downLoadInfo.name);
                                    break;

                                case QuickDownLoadManager.State.STATE_WAITING: // 等待中
                                    Log.i(TAG, "等待中:" + downLoadInfo.name);
                                    break;

                                case QuickDownLoadManager.State.STATE_START:  //  开始下载
                                    Log.i(TAG, "开始下载:" + downLoadInfo.name);
                                    break;


                                case QuickDownLoadManager.State.STATE_DOWNLOADING: // 下载中
                                    Log.i(TAG, "下载中:" + downLoadInfo.name  +"---->>>"+ downLoadInfo.percent +"%");
                                    break;

                                case QuickDownLoadManager.State.STATE_PAUSE:  // 暂停
                                    Log.i(TAG, "暂停:" + downLoadInfo.name);
                                    break;

                                case QuickDownLoadManager.State.STATE_FINISH:  // 下载完成
                                    Log.i(TAG, "下载完成:" + downLoadInfo.name);
                                    break;

                                case QuickDownLoadManager.State.STATE_ERROR:  // 下载出错
                                    Log.i(TAG, "下载出错:" + downLoadInfo.name);
                                    break;

                                case QuickDownLoadManager.State.STATE_NETERROR:  // 网络错误
                                    Log.i(TAG, "网络错误:" + downLoadInfo.name);
                                    break;
                            }
//                            String s = ((DownLoadInfo) obj).toString();
//                            Log.e(TAG, s);
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 101:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissions[i]);
                        if (showRequestPermission) {
                            init_permission();
                            Log.e(TAG, "始终允许了");
                            return;
                        } else { // false 被禁止了，不在访问
                            Log.e(TAG, "false 被禁止了，不在访问");
                            mShowRequestPermission = false;//已经禁止了
                        }
                    } else {
                        Log.e(TAG, "拒绝了该权限");
                    }
                }
                break;
        }
    }

    private void init_permission() {
        if (getSdkVersionSix()) {
            List<String> mPermissionList = new ArrayList<>();
            for (int i = 0; i < ALL_APP_PERMISSIONS.length; i++) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, ALL_APP_PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED) {
                    mPermissionList.add(ALL_APP_PERMISSIONS[i]);
                }
            }

            if (mPermissionList.isEmpty()) {// 全部允许
                mShowRequestPermission = true;
            } else {//存在未允许的权限
                String[] permissionsArr = mPermissionList.toArray(new String[mPermissionList.size()]);
                ActivityCompat.requestPermissions(MainActivity.this, permissionsArr, 101);
            }
        }
    }


    public boolean getSdkVersionSix() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
