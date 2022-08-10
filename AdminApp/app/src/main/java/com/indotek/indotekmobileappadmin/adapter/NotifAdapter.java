package com.indotek.indotekmobileappadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indotek.indotekmobileappadmin.R;
import com.indotek.indotekmobileappadmin.activities.CameraImagePreview;
import com.indotek.indotekmobileappadmin.datamodel.DataNotif;
import com.indotek.indotekmobileappadmin.datamodel.NotifModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {

    private List<DataNotif> data;
    private Context context;
    private String baseurl;

    public NotifAdapter(List<DataNotif> data, Context context) {
        this.data = data;
        this.context = context;
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.linkapi), Context.MODE_PRIVATE);
        this.baseurl = sharedPreferences.getString(context.getString(R.string.linkapi)," ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_requestfoto, parent, false);
        return new NotifAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        DataNotif d = data.get(position);
        String namapemilik = d.getNamaPemilik();
        String jenismesin = d.getJenisMesin();
        String merek = d.getMerekMesin();
        String idmesin = d.getId_mesin();
        String urlgbr = d.getFotoBefore();
        if (urlgbr != null){
            Picasso.get().load(baseurl + "/" + urlgbr).into(holder.camera);
        }
        holder.pemilik.setText(namapemilik);
        holder.tanggal.setText(d.getTanggalNotif());
        holder.jenismesin.setText(jenismesin);
        holder.merek.setText(merek);
        holder.camera.setOnClickListener(v->{
            Intent i = new Intent(context, CameraImagePreview.class);
            i.putExtra("namapemilik",namapemilik);
            i.putExtra("jenismesin",jenismesin);
            i.putExtra("merek",merek);
            i.putExtra("id_mesin",idmesin);
            i.putExtra("urlgbr",urlgbr);
            context.startActivity(i);
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tanggal,jenismesin,pemilik,merek;
        ImageView camera;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            jenismesin = itemView.findViewById(R.id.jenismesin);
            merek = itemView.findViewById(R.id.merek);
            pemilik = itemView.findViewById(R.id.pemilik);
            camera = itemView.findViewById(R.id.fotomesin);

        }


    }
}
