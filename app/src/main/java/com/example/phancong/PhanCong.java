package com.example.phancong;

import com.google.gson.annotations.SerializedName;

public class PhanCong {

    // --- QUAN TRỌNG: Server yêu cầu chính xác chữ thường/hoa ---
    // Server báo lỗi: "maGV field is required" -> Phải dùng @SerializedName("maGV")

    @SerializedName("maMH")
    private String maMH;

    @SerializedName("maLop")
    private String maLop;

    @SerializedName("maGV")
    private String maGV;

    // Các trường hiển thị (không quan trọng khi gửi POST, nhưng cần cho GET)
    @SerializedName(value = "tenMon", alternate = {"TenMon", "tenMonHoc"})
    private String tenMon;

    @SerializedName(value = "tenGV", alternate = {"TenGV", "hoTen", "hoten"})
    private String tenGV;

    @SerializedName("soTinChi")
    private int soTinChi;

    // Constructor chuẩn
    public PhanCong(String maMH, String maLop, String maGV) {
        this.maMH = maMH;
        this.maLop = maLop;
        this.maGV = maGV;
    }

    // --- Getter & Setter ---
    public String getMaMH() { return maMH; }
    public String getMaLop() { return maLop; }
    public String getMaGV() { return maGV; }

    public String getTenMon() { return tenMon; }
    public String getTenGV() { return tenGV; }
    public int getSoTinChi() { return soTinChi; }
}