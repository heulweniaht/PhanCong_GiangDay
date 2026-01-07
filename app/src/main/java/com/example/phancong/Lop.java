package com.example.phancong;

import com.google.gson.annotations.SerializedName;

public class Lop {
    @SerializedName(value = "maLop", alternate = {"MaLop", "malop"})
    private String maLop;

    @SerializedName(value = "tenLop", alternate = {"TenLop", "tenlop"})
    private String tenLop;

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }
    @Override
    public String toString(){
        return maLop;
    }
}
