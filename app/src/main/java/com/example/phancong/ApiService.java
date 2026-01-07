package com.example.phancong;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/PhanCongGiangDay")
    Call<List<PhanCong>> getPhanCong(@Query("maLop") String maLop);

    // Lưu phân công (Thêm mới hoặc Cập nhật)
    @Headers("Content-Type: application/json")
    @POST("api/PhanCongGiangDay")
    Call<Void> savePhanCong(@Body PhanCong body);
    // Xóa phân công
    @DELETE("api/PhanCongGiangDay")
    Call<Void> deletePhanCong(@Query("maLop") String maLop, @Query("maMH") String maMH);

    // Các API hỗ trợ lấy dữ liệu cho Spinner
    @GET("api/Lop")
    Call<List<Lop>> getDsLop();

    @GET("api/MonHoc")
    Call<List<MonHoc>> getDsMonHoc();

    @GET("api/GiaoVien")
    Call<List<GiaoVien>> getDsGiaoVien();
}
