package com.example.phancong;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.SerializedName;

public class PhanCongRequest {

    @SerializedName("maLop")
    private String maLop;

    @SerializedName("maMH")
    private String maMH;

    @SerializedName("maGV")
    private String maGV;

    // Constructor đầy đủ
    public PhanCongRequest(String maLop, String maMH, String maGV) {
        this.maLop = maLop;
        this.maMH = maMH;
        this.maGV = maGV;
    }

    // Getters and Setters
    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getMaMH() { return maMH; }
    public void setMaMH(String maMH) { this.maMH = maMH; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }
}