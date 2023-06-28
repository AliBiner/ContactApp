package com.example.lecture10_rehber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;

public class GosterActivity extends AppCompatActivity {
    VeriTabani vt;
    private RecyclerView rvGoster;
    ArrayList<Kullanici> kullaniciListe;
    Donustur donustur;
    Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goster);
        rvGoster=findViewById(R.id.rvGoster);
        vt=new VeriTabani(this);
        kullaniciListe=new ArrayList<>();
    }
    public void Listele(View v){
        try{
            SQLiteDatabase db=vt.getReadableDatabase();
            if(db!=null){
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                linearLayoutManager.scrollToPosition(0);
                rvGoster.setLayoutManager(linearLayoutManager);
                kullaniciListe.clear();
                ArrayList<Kullanici> kullaniciListesi= vt.Listele(db,kullaniciListe);
                rvGoster.setHasFixedSize(true);
                donustur=new Donustur(context,kullaniciListesi);
                rvGoster.setAdapter(donustur);
            }else{
                Toast.makeText(this, "Veritabanı Boş", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception ex){
            Toast.makeText(this, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}