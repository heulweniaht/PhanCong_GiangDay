package com.example.phancong;

import com.google.gson.annotations.SerializedName;

public class GiaoVien {
    String maGV;
    @SerializedName("hoten")
    String tenGV;

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getTenGV() {
        return tenGV;
    }

    public void setTenGV(String tenGV) {
        this.tenGV = tenGV;
    }
    @Override
    public String toString(){
        return tenGV;
    }
}
