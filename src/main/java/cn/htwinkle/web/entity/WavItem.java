package cn.htwinkle.web.entity;

import cn.htwinkle.web.kit.FileKit;

import java.io.File;
import java.util.Date;

public class WavItem {
    private String fileName;
    private String downloadPath;
    private Date recordTime;
    private String content;
    private String title;
    private String fileSize;

    public WavItem() {
    }

    public WavItem(File file) {
        this.fileName = file.getName();
        this.downloadPath = file.getName();
        this.fileSize = FileKit.getPrintSize(file.length());
    }

    public String getFileName() {
        return fileName;
    }

    public WavItem setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public WavItem setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
        return this;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public WavItem setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public String getContent() {
        return content;
    }

    public WavItem setContent(String content) {
        this.content = content;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public WavItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getFileSize() {
        return fileSize;
    }

    public WavItem setFileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }
}
