package net.bag12.glowbeauty;

public class Kategori {
    private String nama;
    private String imgUrl;

    public Kategori(){
    }

    public Kategori(String nama, String imgUrl) {
        this.nama = nama;
        if (imgUrl.trim().equals("")) {
            imgUrl = "NoName";
        }
        this.imgUrl = imgUrl;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
