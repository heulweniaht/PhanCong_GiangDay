package com.example.phancong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhanCongAdapter extends RecyclerView.Adapter<PhanCongAdapter.ViewHolder> {
    List<PhanCong> list;
    OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(PhanCong item);
    }

    public PhanCongAdapter(List<PhanCong> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phan_cong,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        PhanCong item = list.get(position);
        holder.tvTenMon.setText(item.getTenMon());
        holder.tvTenGV.setText("GV: " + item.getTenGV());
        holder.tvTinChi.setText(item.getSoTinChi() + " TC");

        // Sự kiện click vào item
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount(){
        return list == null ? 0 : list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenMon, tvTenGV, tvTinChi;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvTenMon = itemView.findViewById(R.id.tvTenMon);
            tvTenGV = itemView.findViewById(R.id.tvTenGV);
            tvTinChi = itemView.findViewById(R.id.tvTinChi);
        }
    }
}
