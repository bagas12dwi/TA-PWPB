package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class upload_kategori extends AppCompatActivity {

    public static final int CHOOSE_IMAGE=1;

    private Button simpanGambar;
    private ImageView imgKategori;
    private ProgressBar pgbar;
    private EditText etNama;

    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mDbRef;

    private StorageTask mStorageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_kategori);
        simpanGambar = findViewById(R.id.btnSimpan);
        imgKategori = findViewById(R.id.imgupload);
        etNama = findViewById(R.id.nama_kategori);
        pgbar = findViewById(R.id.uploadprogressBar);

        mStorageRef = FirebaseStorage.getInstance().getReference("kategori");
        mDbRef = FirebaseDatabase.getInstance().getReference("kategori");

        simpanGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStorageTask != null && mStorageTask.isInProgress()){
                    Toast.makeText(upload_kategori.this, "Sedang Proses !", Toast.LENGTH_SHORT).show();
                    pgbar.setVisibility(View.VISIBLE);
                }else {
                    uploadGambar();
                    pgbar.setVisibility(View.VISIBLE);
                }
            }
        });

        imgKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, CHOOSE_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUrl = data.getData();
            Picasso.with(this).load(imgUrl).into(imgKategori);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void clear(){
        etNama.setText("");
    }

    private void uploadGambar() {
        if (imgUrl != null){
            final StorageReference sRef = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imgUrl));

            mStorageTask = sRef.putFile(imgUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pgbar.setProgress(0);
                        }
                    }, 500);

                    sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Kategori kategori = new Kategori(
                                    etNama.getText().toString().trim(),
                                    uri.toString()
                            );
                            String uploadID = mDbRef.push().getKey();
                            mDbRef.child(uploadID).setValue(kategori);
                            Toast.makeText(upload_kategori.this, "Berhasil disimpan !", Toast.LENGTH_SHORT).show();
                            imgKategori.setImageResource(R.drawable.ic_image_black_24dp);
                            clear();
                            pgbar.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload_kategori.this, "ini Salah", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    pgbar.setProgress((int) progress);
                }
            });
        }else {
            Toast.makeText(this, "Gambar belum Dipilih !", Toast.LENGTH_SHORT).show();
        }
    }
}
