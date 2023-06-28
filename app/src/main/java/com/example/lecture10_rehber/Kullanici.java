package com.example.lecture10_rehber;

public class Kullanici {

    String ad, email,telefon, not,image,ID;
    public Kullanici(String ID, String ad, String email, String telefon, String not, String image) {
        this.ID = ID;
        this.ad = ad;
        this.email = email;
        this.telefon = telefon;
        this.not = not;
        this.image=image;
    }
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = this.email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getNot(){return not;}
    public void setNot(String not){this.not = this.not;}

    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}
}
