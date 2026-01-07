package com.example.phancong;

import com.google.gson.annotations.SerializedName;

public class MonHoc {
    @SerializedName(value = "maMH", alternate = {"MaMH", "mamh"})
    private String maMH;

    // Dự phòng server trả về tenMon, TenMon hoặc tenMonHoc
    @SerializedName(value = "tenMon", alternate = {"TenMon", "tenmon", "tenMonHoc", "TenMonHoc"})
    private String tenMon;

    @SerializedName(value = "soTinChi", alternate = {"SoTinChi", "sotinchi"})
    private int soTinChi;

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }
    @Override
    public String toString(){
        return tenMon;
    }
}
