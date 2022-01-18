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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class upload_masker extends AppCompatActivity {
    public static final int CHOOSE_IMAGE=1;

    FirebaseHelper firebaseHelper;

    private Button pilihGambar, simpanGambar;
    private ImageView imgView;
    private ProgressBar pgbar;
    private EditText etNama, etHarga, etKegunaan;
    private Spinner spMerk;

    private Uri imgUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mdbRef, db;

    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> dataSpinner;

    private StorageTask mStoragetask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_masker);
        pilihGambar = (Button) findViewById(R.id.btnUpload);
        simpanGambar = (Button) findViewById(R.id.btnSimpan);
        imgView = (ImageView) findViewById(R.id.imgupload);
        pgbar = (ProgressBar) findViewById(R.id.uploadprogressBar);
        etNama = (EditText) findViewById(R.id.nama_masker);
        etHarga = (EditText) findViewById(R.id.harga_masker);
        etKegunaan = (EditText) findViewById(R.id.kegunaan);
        spMerk = (Spinner) findViewById(R.id.spinMerk);

        mStorageRef = FirebaseStorage.getInstance().getReference("masker");
        mdbRef = FirebaseDatabase.getInstance().getReference("masker");
        db = FirebaseDatabase.getInstance().getReference("kategori");

        dataSpinner = new ArrayList<>();
        adapter = new ArrayAdapter<String>
                (upload_masker.this,android.R.layout.simple_spinner_dropdown_item, dataSpinner);

        spMerk.setAdapter(adapter);
        retriveData();

        simpanGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStoragetask != null && mStoragetask.isInProgress()){
                    Toast.makeText(upload_masker.this, "Sedang Proses !", Toast.LENGTH_SHORT).show();
                }else {
                    uploadGambar();
                }
            }
        });

        pilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ambilGambar();
            }
        });

    }

    public void retriveData(){
        listener = db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Kategori ktgr = snapshot.getValue(Kategori.class);
                    String nama = ktgr.getNama();
                    dataSpinner.add(nama);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void ambilGambar(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data != null && data.getData() != null){
            imgUrl = data.getData();

            Picasso.with(this).load(imgUrl).into(imgView);
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void clear(){
        etNama.setText("");
        etHarga.setText("");
        etKegunaan.setText("");
    }

    private void uploadGambar(){
        if (imgUrl!= null){
            final StorageReference sRef = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imgUrl));

            mStoragetask = sRef.putFile(imgUrl)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
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
                                    String merk = spMerk.getSelectedItem().toString().trim();
                                    Upload upload = new Upload(
                                            merk,
                                            etNama.getText().toString().trim(),
                                            etKegunaan.getText().toString().trim(),
                                            etHarga.getText().toString().trim(),
                                            uri.toString()
                                    );
                                    String uploadID = mdbRef.push().getKey();
                                    mdbRef.child(uploadID).setValue(upload);
                                    Toast.makeText(upload_masker.this, "Berhasil disimpan !", Toast.LENGTH_SHORT).show();
                                    imgView.setImageResource(R.drawable.ic_image_black_24dp);
                                    clear();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(upload_masker.this, "ini Salah", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            pgbar.setProgress((int) progress);
                        }
                    });
        }else {
            Toast.makeText(upload_masker.this, "File Belum Dipilih !", Toast.LENGTH_SHORT).show();
        }
    }
}
