package net.bag12.glowbeauty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Kategori> mKategori;

    public KategoriAdapter(Context mContext, List<Kategori> mKategori) {
        this.mContext = mContext;
        this.mKategori = mKategori;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.kategori_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        Kategori kategori = mKategori.get(position);
        holder.tvNamaKategori.setText(kategori.getNama());
        Picasso.with(mContext)
                .load(kategori.getImgUrl())
                .placeholder(R.drawable.ic_image_black_24dp)
                .fit()
                .centerCrop()
                .into(holder.ivKaegori);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mNama = holder.tvNamaKategori.getText().toString();
                Intent i = new Intent(mContext, list_masker.class);
                i.putExtra("Merk", mNama);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mKategori.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView tvNamaKategori;
        public ImageView ivKaegori;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKategori = itemView.findViewById(R.id.txNamaKategori);
            ivKaegori = itemView.findViewById(R.id.imgKategori);
        }
    }
}
