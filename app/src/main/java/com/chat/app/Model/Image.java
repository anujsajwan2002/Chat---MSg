package com.chat.app.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
public class Image {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Image(String id, String filename, String contentType, byte[] data, long size, String uploadDate) {
        this.id = id;
        this.filename = filename;
        this.contentType = contentType;
        this.data = data;
        this.size = size;
        this.uploadDate = uploadDate;
    }

    @Id
    private String id;
    private String filename;
    private String contentType;
    private byte[] data;
    private long size;
    private String uploadDate;
}
