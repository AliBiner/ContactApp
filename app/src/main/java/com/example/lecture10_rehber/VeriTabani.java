package com.example.lecture10_rehber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class VeriTabani  extends SQLiteOpenHelper {
    private static final String VERITABANI_ADI="ogrenciler.db";
    private static final String TABLO_OGRENCILER="ogrenci"; //tablo adı kisiler
    private static final String ROW_ID="ID";
    private static final String ROW_AD="Adi";
    private static final String ROW_EMAIL ="Email";
    private static final String ROW_TEL="Telefon";
    private static final String ROW_NOT ="Notlar";
    private static final String ROW_IMAGE ="Resimler";
    private static final int VeritabaniVersion=1;
    Context context;
    public VeriTabani(Context context){
        super(context, VERITABANI_ADI, null, VeritabaniVersion);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tabloOlustur="CREATE TABLE "+TABLO_OGRENCILER+" ("+
                ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                ROW_AD+" TEXT, "+
                ROW_EMAIL +" TEXT, "+
                ROW_TEL+" TEXT, " +
                ROW_NOT + " TEXT, " +
                ROW_IMAGE + " TEXT);";
        db.execSQL(tabloOlustur);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLO_OGRENCILER);
        onCreate(db);
    }
    public long Ekle(SQLiteDatabase db, String adi, String email, String telefon, String not, String image){
        ContentValues cv=new ContentValues();
        cv.put("Adi",adi);
        cv.put("Email",email);
        cv.put("Telefon",telefon);
        cv.put("Notlar",not);
        cv.put("Resimler",image);
        long sonuc=-1;
        try{
            sonuc=db.insert(TABLO_OGRENCILER,null,cv);
        }catch(Exception ex){
            Toast.makeText(context, "Ekleme Hatası:"+ex.toString(), Toast.LENGTH_SHORT).show();
        }
        return sonuc;
    }
    public long Ekle(String adi, String unvan, String telefon, String adres, String resim){
        SQLiteDatabase db=this.getWritableDatabase();
        Toast.makeText(context, ""+telefon, Toast.LENGTH_SHORT).show();
        ContentValues cv=new ContentValues();
        cv.put(ROW_AD,adi);
        cv.put(ROW_EMAIL,unvan);
        cv.put(ROW_TEL,telefon);
        cv.put(ROW_NOT,adres);
        cv.put(ROW_IMAGE,resim);
        long sonuc=-1;
        try{
            sonuc=db.insert(TABLO_OGRENCILER,null,cv);
        }catch(Exception ex){
            db.close();
            Log.d("Hata", ex.toString());
            Log.i("Hata",ex.toString());
            Toast.makeText(context, "Ekleme Hatası:"+ex.toString(), Toast.LENGTH_LONG).show();
        }
        db.close();
        return sonuc;
    }
    public ArrayList<Kullanici> Listele(SQLiteDatabase db, ArrayList<Kullanici> kullaniciListesi){
        ArrayList<Kullanici> dizi = new ArrayList<>();
        String sorgu="select * from "+TABLO_OGRENCILER;
        StringBuffer buffer=new StringBuffer();
        Cursor cursor=db.rawQuery(sorgu,null);
        if(cursor.getCount()==0){
            Toast.makeText(context, "Veri Yok", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                String ID=cursor.getString(0);
                String Adi=cursor.getString(1);
                String Email=cursor.getString(2);
                String Telefon=cursor.getString(3);
                String Not=cursor.getString(4);
                String Image = cursor.getString(5);
                kullaniciListesi.add(new Kullanici(ID,Adi,Email,Telefon,Not,Image));
            }
        }
        return kullaniciListesi;
    }
    public Cursor getDataById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLO_OGRENCILER + " WHERE " + ROW_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};
        return db.rawQuery(query, selectionArgs);
    }
    public void deleteDataById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = ROW_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLO_OGRENCILER, whereClause, whereArgs);
    }


}
