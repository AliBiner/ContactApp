package com.example.lecture10_rehber;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Donustur extends RecyclerView.Adapter<Donustur.OzelViewHolder>{

    ArrayList<Kullanici> kullaniciListesi;
    LayoutInflater layoutInflater;
    Context context;

    public Donustur(Context context, ArrayList<Kullanici> kullaniciListesi) {
        layoutInflater=LayoutInflater.from(context);
        this.kullaniciListesi=kullaniciListesi;
        this.context=context;
        Toast.makeText(context, ""+kullaniciListesi.get(0).getAd(), Toast.LENGTH_SHORT).show();
    }



    @NonNull
    @Override
    public OzelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RelativeLayout teksatir=(RelativeLayout) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tek_satir,parent,false);
        return new OzelViewHolder(teksatir);
    }

    @Override
    public void onBindViewHolder(@NonNull OzelViewHolder holder,final int position) {
        holder.tvAdi.setText(kullaniciListesi.get(position).getAd());
        holder.tvTelefon.setText(kullaniciListesi.get(position).getTelefon());
        holder.ivProfil.setImageURI(Uri.parse(String.valueOf(kullaniciListesi.get(position).getImage())));
        holder.rlIcerik.setTag(holder);
        holder.rlIcerik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OzelViewHolder holder=(OzelViewHolder)v.getTag();
                int position=holder.getLayoutPosition();
                Intent intent = new Intent(v.getContext(),DetayActivity.class);
                intent.putExtra("Id",kullaniciListesi.get(holder.getAdapterPosition()).getID());
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return kullaniciListesi.size();
    }
    class OzelViewHolder extends RecyclerView.ViewHolder {
        TextView tvAdi,tvTelefon;
        ImageView ivProfil;
        RelativeLayout rlIcerik;
        RecyclerView rvGoster;
        public OzelViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAdi=itemView.findViewById(R.id.tvAdi);
            tvTelefon=itemView.findViewById(R.id.tvTelefon);
            ivProfil =itemView.findViewById(R.id.ivProfil);
            rvGoster=itemView.findViewById(R.id.rvGoster);
            rlIcerik=itemView.findViewById(R.id.rlIcerik);
        }
    }
}
