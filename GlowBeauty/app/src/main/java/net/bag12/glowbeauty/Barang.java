package net.bag12.glowbeauty;

public class Barang {

    private String nama;
    private String nama_masker1;
    private String kegunaan1;
    private String harga_masker1;
    private String qty_stock1;
    private String alamat;

    public Barang(){

    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_masker1() {
        return nama_masker1;
    }

    public void setNama_masker1(String nama_masker1) {
        this.nama_masker1 = nama_masker1;
    }

    //
//    public String getNama_masker() {
//        return nama_masker1;
//    }
//
//    public void setNama_masker1(String nama_masker1) {
//        this.nama_masker1 = nama_masker1;
//    }

    public String getKegunaan1() {
        return kegunaan1;
    }

    public void setKegunaan1(String kegunaan1) {
        this.kegunaan1 = kegunaan1;
    }

    public String getHarga_masker1() {
        return harga_masker1;
    }

    public void setHarga_masker1(String harga_masker1) {
        this.harga_masker1 = harga_masker1;
    }

    public String getQty_stock1() {
        return qty_stock1;
    }

    public void setQty_stock1(String qty_stock1) {
        this.qty_stock1 = qty_stock1;
    }

    @Override
    public String toString() {
        return " "+nama+"\n"+
                " "+nama_masker1+"\n" +
                " "+kegunaan1 +"\n" +
                " "+qty_stock1 +"\n" +
                " "+harga_masker1+"\n" +
                " "+alamat;
    }

    public Barang(String nm1, String nm, String kgn, String hrg, String qty, String almt){
        nama = nm1;
        nama_masker1 = nm;
        kegunaan1 = kgn;
        harga_masker1 = hrg;
        qty_stock1 = qty;
        alamat = almt;
    }
}
