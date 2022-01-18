package net.bag12.glowbeauty;

import android.content.Context;
import android.content.SearchRecentSuggestionsProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class pembeliAdapter extends RecyclerView.Adapter<pembeliAdapter.ViewHolder> {

    private ArrayList<Barang> listBarang;
    private Context context;

    public pembeliAdapter(ArrayList<Barang> listBarang, Context context){
        this.listBarang = listBarang;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_nama, tv_namaMasker, tv_jumlah, tv_harga, tv_alamat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nama = itemView.findViewById(R.id.namaPembeli);
            tv_namaMasker = itemView.findViewById(R.id.tvnamaMasker);
            tv_jumlah = itemView.findViewById(R.id.jumlah);
            tv_harga = itemView.findViewById(R.id.harga);
            tv_alamat = itemView.findViewById(R.id.alamat);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pembeli_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String namaPembeli = listBarang.get(position).getNama();
        final String namaMasker = listBarang.get(position).getNama_masker1();
        final String jumlah = listBarang.get(position).getQty_stock1();
        final String harga = listBarang.get(position).getHarga_masker1();
        final String alamat = listBarang.get(position).getAlamat();

        holder.tv_nama.setText(namaPembeli);
        holder.tv_namaMasker.setText(namaMasker);
        holder.tv_jumlah.setText(jumlah);
        holder.tv_harga.setText(harga);
        holder.tv_alamat.setText(alamat);
    }

    @Override
    public int getItemCount() {
        return listBarang.size();
    }
}
