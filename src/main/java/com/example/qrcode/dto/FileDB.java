package com.example.qrcode.dto;


public class FileDB {
    private String filename;
    private byte[] data;

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getData() {
        return data;
    }

    public String getFilename() {
        return filename;
    }
}
