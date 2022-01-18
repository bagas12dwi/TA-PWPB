package net.bag12.glowbeauty;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class manageMasker_adapter extends RecyclerView.Adapter<manageMasker_adapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUpload;

    public manageMasker_adapter(Context mContext, List<Upload> mUpload) {
        this.mContext = mContext;
        this.mUpload = mUpload;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.masker_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        Upload upld = mUpload.get(position);
        holder.nama.setText(upld.getNama());
        holder.harga.setText(upld.getHarga());
        holder.desc.setText(upld.getKegunaan());
        holder.jenis.setText(upld.getMerk());
        Picasso.with(mContext)
                .load(upld.getImgUrl())
                .placeholder(R.drawable.ic_image_black_24dp)
                .fit()
                .centerCrop()
                .into(holder.img_masker);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String mNama = holder.nama.getText().toString();
                final Query mQuery = FirebaseDatabase.getInstance().getReference("masker").orderByChild("nama")
                        .equalTo(mNama);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin ingin menghapus?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(mContext, "Berhasil dihapus !", Toast.LENGTH_SHORT).show();
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
        return mUpload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama;
        public TextView harga;
        public TextView desc;
        public TextView jenis;
        public ImageView img_masker;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            nama = itemView.findViewById(R.id.nama);
            harga = itemView.findViewById(R.id.harga_masker);
            img_masker = itemView.findViewById(R.id.imgMasker);
            desc = itemView.findViewById(R.id.desk);
            jenis = itemView.findViewById(R.id.jnsMasker);
        }
    }
}
