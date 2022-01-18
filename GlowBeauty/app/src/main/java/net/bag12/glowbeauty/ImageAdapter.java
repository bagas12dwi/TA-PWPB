package net.bag12.glowbeauty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUpload;

    public ImageAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUpload = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageView imgv = v.findViewById(R.id.imgMasker);

                Drawable mDrawable = imgv.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable)mDrawable).getBitmap();
                String mNama = holder.nama.getText().toString();
                String mHarga = holder.harga.getText().toString();
                String mDesc = holder.desc.getText().toString();
                String mJenis = holder.jenis.getText().toString();

                Intent intent = new Intent(mContext, spesifikasi_masker.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte [] bytes = stream.toByteArray();
                intent.putExtra("imgurl", bytes);
                intent.putExtra("nama", mNama);
                intent.putExtra("harga", mHarga);
                intent.putExtra("desc", mDesc);
                intent.putExtra("jenis", mJenis);
                    mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUpload.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

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
