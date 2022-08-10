package com.indotek.mobileindotek.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.indotek.mobileindotek.R;
import com.indotek.mobileindotek.activities.DetailMesinActivity;
import com.indotek.mobileindotek.datamodel.ItemRVUtamaModel;

import java.util.List;

public class OrderanAdapter extends RecyclerView.Adapter<OrderanAdapter.ViewHolder> {

    List<ItemRVUtamaModel> data;
    Context context;

    public OrderanAdapter(List<ItemRVUtamaModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rvutama, parent, false);
        return new OrderanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderanAdapter.ViewHolder holder, int position) {
        ItemRVUtamaModel d = data.get(position);
        String nama,keterangan,tgl,idmesin,status;
        nama = d.getJenis();
        keterangan = d.getKeluhan();
        tgl = d.getTanggal();
        idmesin = d.getIdMesin();
        status = d.getStatus();

        switch (d.getStatus()){
            case "1":
                holder.tvstatus.setText("Diagnosa");
                break;
            case "2":
                holder.tvstatus.setText("Tunggu Acc");
                break;
            case "3":
                holder.tvstatus.setText("Acc Biaya");
                break;
            case "4":
                holder.tvstatus.setText("Selesai");
                break;
        }
        holder.keterangan.setText(keterangan);
        holder.nama.setText(nama);
        holder.tgl.setText(tgl);
        holder.body.setOnClickListener(v->{
            Intent i = new Intent(context, DetailMesinActivity.class);
            i.putExtra("idmesin",idmesin);
            i.putExtra("statusmesin",status);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama,tgl,keterangan,tvstatus;
        ConstraintLayout body;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvnama);
            tgl = itemView.findViewById(R.id.tvtgl);
            keterangan = itemView.findViewById(R.id.tvketerangan);
            body = itemView.findViewById(R.id.body);
            tvstatus = itemView.findViewById(R.id.tvstatus);
        }
    }
}
