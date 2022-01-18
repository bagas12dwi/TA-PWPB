package net.bag12.glowbeauty;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Manage_KategoriAdapter extends RecyclerView.Adapter<Manage_KategoriAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Kategori> mKategori;

    public Manage_KategoriAdapter(Context mContext, List<Kategori> mKategori) {
        this.mContext = mContext;
        this.mKategori = mKategori;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.mkategori_item, parent, false);
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String mNama = holder.tvNamaKategori.getText().toString();
                final Query mQuery = FirebaseDatabase.getInstance().getReference("kategori")
                        .orderByChild("nama").equalTo(mNama);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin menghapus kategori ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds:dataSnapshot.getChildren()){
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(mContext, "Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(mContext, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
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
