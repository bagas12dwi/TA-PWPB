package net.bag12.glowbeauty;

public class Upload {
    private String merk;
    private String nama;
    private String kegunaan;
    private String harga;
    private String imgUrl;

    public Upload() {
    }

    public Upload(String merk, String nama, String kegunaan, String harga, String imgUrl) {
        if (imgUrl.trim().equals("")){
            imgUrl = "NoName";
        }
        this.merk = merk;
        this.nama = nama;
        this.kegunaan = kegunaan;
        this.harga = harga;
        this.imgUrl = imgUrl;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKegunaan() {
        return kegunaan;
    }

    public void setKegunaan(String kegunaan) {
        this.kegunaan = kegunaan;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
