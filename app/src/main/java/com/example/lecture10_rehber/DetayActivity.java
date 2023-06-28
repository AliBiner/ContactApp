package com.example.lecture10_rehber;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class DetayActivity extends AppCompatActivity {
    TextView tvDetayAdi, tvDetayEmail, tvDetayTelefon, tvDetayNot;
    ImageView ivDetayProfil;
    String id,Adi,Telefon;
    FloatingActionButton btnSil,btnAra;
    static int PERMISSION_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);
        VeriTabani vt = new VeriTabani(this);
        tvDetayAdi =findViewById(R.id.tvDetayAdi);
        tvDetayEmail =findViewById(R.id.tvDetayEmail);
        tvDetayTelefon =findViewById(R.id.tvDetayTelefon);
        tvDetayNot = findViewById(R.id.tvDetayNot);
        ivDetayProfil =findViewById(R.id.ivDetayProfil);
        btnSil=findViewById(R.id.btnSil);
        btnAra=findViewById(R.id.btnAra);
        this.id = getIntent().getExtras().getString("Id");
        Cursor cursor = vt.getDataById(id);
        if (cursor != null && cursor.moveToFirst()) {
            Adi=cursor.getString(1);
            String Email=cursor.getString(2);
            Telefon=cursor.getString(3);
            String Not=cursor.getString(4);
            String Image = cursor.getString(5);
            ivDetayProfil.setImageURI(Uri.parse(Image));
            tvDetayAdi.setText(Adi);
            tvDetayEmail.setText(Email);
            tvDetayTelefon.setText(Telefon);
            tvDetayNot.setText(Not);
            cursor.close();
        }
        btnSil.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ad: "+ Adi);
            builder.setMessage("Silmek istediğinize emin misiniz?");
            builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    VeriTabani vt = new VeriTabani(DetayActivity.this);
                    vt.deleteDataById(id);
                    finish();
                    Toast.makeText(DetayActivity.this, "Silindi.", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
        });

        if (ContextCompat.checkSelfPermission(DetayActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(DetayActivity.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }

        btnAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+Telefon));
                startActivity(intent);
            }
        });
    }
}