package com.mapscloud.quickdownload.bean;

/**
 * @author TomCan
 * @description:
 * @date :2020/6/12 10:04
 */
public class DownLoadInfo {
    public String ID;
    public int    QRCode;
    public String tag;
    public String url;
    public long   currentLength;
    public long   size;
    public String path;
    public String name;
    public int    percent;


    public DownLoadInfo() {
    }

    public DownLoadInfo(String ID, int QRCode, String tag, String url, long currentLength, long size, String path, String name, int percent) {
        this.ID = ID;
        this.QRCode = QRCode;
        this.tag = tag;
        this.url = url;
        this.currentLength = currentLength;
        this.size = size;
        this.path = path;
        this.name = name;
        this.percent = percent;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getQRCode() {
        return QRCode;
    }

    public void setQRCode(int QRCode) {
        this.QRCode = QRCode;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }


    @Override
    public String toString() {
        return "DownLoadInfo{" +
                "ID='" + ID + '\'' +
                ", QRCode=" + QRCode +
                ", tag='" + tag + '\'' +
                ", url='" + url + '\'' +
                ", currentLength=" + currentLength +
                ", size=" + size +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", percent=" + percent +
                '}';
    }
}
