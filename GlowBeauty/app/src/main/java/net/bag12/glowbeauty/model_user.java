package net.bag12.glowbeauty;

import java.io.Serializable;

public class model_user implements Serializable {
    private String nama;
    private String alamat;
    private String noHp;
    private String email;
    private String pass;
    private String level;
    private String key;

    public model_user(){
    }

    public model_user(String nama, String alamat, String noHp, String email, String pass, String level) {
        this.nama = nama;
        this.alamat = alamat;
        this.noHp = noHp;
        this.email = email;
        this.pass = pass;
        this.level = level;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

