package com.example.phancong;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Spinner spnLop;
    RecyclerView rcvPhanCong;
    FloatingActionButton fabAdd;

    PhanCongAdapter adapter;
    List<PhanCong> listPhanCong = new ArrayList<>();

    List<Lop> listLop = new ArrayList<>();
    List<MonHoc> listMonHoc = new ArrayList<>();
    List<GiaoVien> listGiaoVien = new ArrayList<>();

    Lop selectedLop = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnLop = findViewById(R.id.spnLop);
        rcvPhanCong = findViewById(R.id.rcvPhanCong);
        fabAdd = findViewById(R.id.fabAdd);

        rcvPhanCong.setLayoutManager(new LinearLayoutManager(this));

        loadAllMetaData();

        spnLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLop = listLop.get(position);
                // Khi chọn lớp xong -> Load danh sách phân công
                loadPhanCong(selectedLop.getMaLop());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        fabAdd.setOnClickListener(v -> showDialog(null));
    }

    private void loadPhanCong(String maLop) {
        ApiClient.getService().getPhanCong(maLop).enqueue(new Callback<List<PhanCong>>() {
            @Override
            public void onResponse(Call<List<PhanCong>> call, Response<List<PhanCong>> response) {
                if(response.isSuccessful()){
                    listPhanCong = response.body();
                    adapter = new PhanCongAdapter(listPhanCong, item -> showDialog(item));
                    rcvPhanCong.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<PhanCong>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi tải phân công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAllMetaData() {
        ApiClient.getService().getDsLop().enqueue(new Callback<List<Lop>>() {
            @Override
            public void onResponse(Call<List<Lop>> call, Response<List<Lop>> response) {
                if(response.isSuccessful() && response.body() != null){
                    listLop = response.body();
                    ArrayAdapter<Lop> adapterLop = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_item, listLop);
                    adapterLop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnLop.setAdapter(adapterLop);
                }
            }

            @Override
            public void onFailure(Call<List<Lop>> call, Throwable t) {

            }
        });

        ApiClient.getService().getDsMonHoc().enqueue(new Callback<List<MonHoc>>() {
            @Override
            public void onResponse(Call<List<MonHoc>> call, Response<List<MonHoc>> response) {
                if(response.isSuccessful()) listMonHoc = response.body();
            }

            @Override
            public void onFailure(Call<List<MonHoc>> call, Throwable t) {

            }
        });

        ApiClient.getService().getDsGiaoVien().enqueue(new Callback<List<GiaoVien>>() {
            @Override
            public void onResponse(Call<List<GiaoVien>> call, Response<List<GiaoVien>> response) {
                if(response.isSuccessful()){
                    listGiaoVien = response.body();
                    android.util.Log.d("DEBUG_APP", "Tổng số GV tải về: " + listGiaoVien.size());
                    for (GiaoVien gv : listGiaoVien) {
                        // In ra từng giáo viên để xem tên có bị null không
                        android.util.Log.d("DEBUG_APP", "GV: " + gv.getTenGV() + " - Mã: " + gv.getMaGV());
                    }
                    Toast.makeText(MainActivity.this, "Đã tải xong " + listGiaoVien.size() + " giáo viên", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GiaoVien>> call, Throwable t) {
                android.util.Log.e("DEBUG_APP", "Lỗi API: " + t.getMessage());
            }
        });
    }

    private void showDialog(PhanCong phanCongEdit){
        if(selectedLop == null) return;

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_phan_cong);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Spinner spnMon = dialog.findViewById(R.id.spMonHoc);
        Spinner spnGV = dialog.findViewById(R.id.spGiaoVien);
        TextView tvMonHocReadOnly = dialog.findViewById(R.id.tvMonHocReadOnly);
        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnDelete = dialog.findViewById(R.id.btnDelete);

        ArrayAdapter<GiaoVien> adapterGV = new ArrayAdapter<>(this, R.layout.item_spinner_custom, listGiaoVien);
        adapterGV.setDropDownViewResource(R.layout.item_spinner_custom); // Dùng cùng layout cho lúc xổ xuống
        spnGV.setAdapter(adapterGV);

        boolean isUpdate = phanCongEdit != null;
        if(isUpdate){
            //Cập nhật
            spnMon.setVisibility(View.GONE);
            tvMonHocReadOnly.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);

            tvMonHocReadOnly.setText(phanCongEdit.getTenMon());

            for(int i = 0; i < listGiaoVien.size();i++){
                if(listGiaoVien.get(i).getMaGV().equals(phanCongEdit.getMaGV())){
                    spnGV.setSelection(i);
                    break;
                }
            }

            btnDelete.setOnClickListener(v -> {
                ApiClient.getService().deletePhanCong(selectedLop.getMaLop(),phanCongEdit.getMaMH()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(MainActivity.this, "Đã xóa!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadPhanCong(selectedLop.getMaLop());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            });
        }else {
            //Thêm mới
            spnMon.setVisibility(View.VISIBLE);
            tvMonHocReadOnly.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            //Đổ dữ liệu vào spinner
            ArrayAdapter<MonHoc> adapterMon = new ArrayAdapter<>(this, R.layout.item_spinner_custom, listMonHoc);
            adapterMon.setDropDownViewResource(R.layout.item_spinner_custom);
            spnMon.setAdapter(adapterMon);
        }

        //Xử lý chung
        btnSave.setOnClickListener(v -> {
            String maLop = selectedLop.getMaLop();
            String maMH;
            if(isUpdate){
                maMH = phanCongEdit.getMaMH();
            } else {
                MonHoc selectedMon = (MonHoc) spnMon.getSelectedItem();
                maMH = (selectedMon != null) ? selectedMon.getMaMH() : null;
            }

            GiaoVien selectedGV = (GiaoVien) spnGV.getSelectedItem();
            String maGV = (selectedGV != null) ? selectedGV.getMaGV() : null;


            // 2. IN LOG KIỂM TRA (Xem Logcat thẻ "CHECK_DATA")
            android.util.Log.d("DEBUG_SEND", "Lop: " + maLop + " - MH: " + maMH + " - GV: " + maGV);

            if (maLop == null || maMH == null || maGV == null) {
                Toast.makeText(MainActivity.this, "Dữ liệu bị thiếu! Kiểm tra lại Spinner.", Toast.LENGTH_SHORT).show();
                return;
            }

            PhanCongRequest data = new PhanCongRequest(maLop, maMH, maGV);
            Gson gson = new Gson();
            String jsonCheck = gson.toJson(data);
            android.util.Log.e("CHECK_JSON", "JSON gửi đi: " + jsonCheck);

            ApiClient.getService().savePhanCong(data).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        loadPhanCong(selectedLop.getMaLop());
                    }else{
                        try {
                            String errorBody = response.errorBody().string(); // Lấy nội dung lỗi server trả về
                            int errorCode = response.code(); // Lấy mã lỗi (400, 404, 500...)

                            android.util.Log.e("Loi_API", "Code: " + errorCode + " - Body: " + errorBody);
                            Toast.makeText(MainActivity.this, "Lỗi " + errorCode + ": " + errorBody, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show();
                }
            });
        });
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}