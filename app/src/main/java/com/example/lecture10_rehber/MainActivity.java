package com.example.lecture10_rehber;
import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    VeriTabani vt;
    private ImageView ivProfil;
    private EditText etAdi, etEmail, etTelefon, etNot;
    private Button btnEkle, btnGoster;
    private static final int CAMERA_PERMISSION_CODE=100;
    private static final int STORAGE_PERMISSION_CODE=200;
    private static final int IMAGE_FROM_GALLERY_CODE=300;
    private static final int IMAGE_FROM_CAMERA_CODE=400;
    private String[] cameraPermission;
    private String[] storagePermission;
    Uri fotoUri;
    private boolean kontrolKameraIzin(){
        boolean cevap = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean cevap2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return cevap & cevap2;
    }
    private  void istekKameraIzin(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_PERMISSION_CODE);
    }
    private boolean kontrolDepoIzin(){
        boolean sonuc1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return sonuc1;
    }
    private  void istekDepoIzin(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_PERMISSION_CODE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        vt=new VeriTabani(this);
        etAdi=findViewById(R.id.etAdi);
        etEmail =findViewById(R.id.etEmail);
        etTelefon=findViewById(R.id.etTelefon);
        etNot = findViewById(R.id.etNot);
        ivProfil = findViewById(R.id.ivProfil);
        btnEkle=findViewById(R.id.btnEkle);
        btnGoster=findViewById(R.id.btnGoster);
        ivProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                istekSecme();
            }
        });
    }
    private void istekSecme() {
        String options[] = {"Kamera", "Galeri"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Birini Seçiniz!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){
                    if (!kontrolKameraIzin()){
                        istekKameraIzin();
                    }else {
                        secKamera();
                    }
                } else if (which==1) {
                    if (!kontrolDepoIzin()){
                        istekDepoIzin();
                    }else {
                        secGaleri();
                    }
                }
            }
        }).create().show();
    }
    private void secGaleri() {
        Intent galeriIntent = new Intent(Intent.ACTION_PICK);
        galeriIntent.setType("image/*");
        startActivityForResult(galeriIntent,IMAGE_FROM_GALLERY_CODE);
    }
    private void secKamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION, "IMAGE_DETAIL");
        fotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent kameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        kameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
        startActivityForResult(kameraIntent, IMAGE_FROM_CAMERA_CODE);
    }
    public void VeritabaniOlustur(View v){
        try {
            vt.getReadableDatabase();
        }catch(Exception ex){
            Toast.makeText(this, "Veritabanı Oluşturulurken Hata Meydana Geldi"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public void Ekle(View v){
        try {
            SQLiteDatabase db=vt.getWritableDatabase();
            if(db!=null){
                String ad=etAdi.getText().toString().trim();
                String email= etEmail.getText().toString().trim();
                String telefon=etTelefon.getText().toString().trim();
                String not = etNot.getText().toString().trim();
                if(!ad.isEmpty() || !email.isEmpty() || !telefon.isEmpty() || !not.isEmpty()){
                    long sonuc=vt.Ekle(db,ad,email,telefon,not,""+ fotoUri);
                    if (sonuc!=-1){
                        Toast.makeText(this, "İşlem Başarılı", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "İşlem Başarısız", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(this, "Veritabanı Boş", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception ex){
            Toast.makeText(this, "Veritabanı Ekleme Sırasında Hata Meydana Geldi"+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Goster(View v){
        startActivity(new Intent(this,GosterActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length>0) {
                    boolean kameraSecildi = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean depoSecildi = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (kameraSecildi && depoSecildi) {
                        secKamera();
                    } else {
                        Toast.makeText(this, "Kamera ve Depolama İzni Gerekli!.", Toast.LENGTH_SHORT).show();
                    }
                }
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length>0) {
                    boolean depoSecildi = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (depoSecildi) {
                        secGaleri();
                    } else {
                        Toast.makeText(this, "Depolama İzni Gerekli!", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK){
            if (requestCode==IMAGE_FROM_GALLERY_CODE){
                fotoUri = data.getData();
                ivProfil.setImageURI(fotoUri);
            }
            else {
                Toast.makeText(this, "İptal Edildi!", Toast.LENGTH_SHORT).show();
            }
        }
    }



}