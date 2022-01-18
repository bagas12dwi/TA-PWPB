package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class spesifikasi_masker extends AppCompatActivity {

    private DatabaseReference database;
    private EditText qty_stock1;
    private Button btn_kirim, btn_kurang, btn_tambah;
    private spesifikasi_masker TextUtilst;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference gwtReference, dbRef;
    private String GetUserId, noUser;
    private TextView tv_user, tv_alamat, nama_masker1, harga_masker1, kegunaan1, more, hide;
    private ImageView iv_masker, ivBack;
    int counter=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spesifikasi_masker);

        final String mJenis = getIntent().getStringExtra("jenis");

        nama_masker1 = (TextView) findViewById(R.id.tv_nama);
        harga_masker1 = (TextView) findViewById(R.id.tvHarga);
        kegunaan1 = (TextView) findViewById(R.id.tvDesc);
        qty_stock1 = (EditText) findViewById(R.id.etPesan);
        btn_kirim = (Button) findViewById(R.id.btnlogin);
        tv_user = (TextView) findViewById(R.id.userpesan);
        tv_alamat = findViewById(R.id.useralamat);
        iv_masker = findViewById(R.id.imgVwMasker);
        btn_kurang = findViewById(R.id.btnKurang);
        btn_tambah = findViewById(R.id.btnTambah);
        more = findViewById(R.id.more);
//        ivBack = findViewById(R.id.imgback);
//
//        ivBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(spesifikasi_masker.this, list_masker.class);
//                i.putExtra("Merk", mJenis);
//                startActivity(i);
//            }
//        });

        database = FirebaseDatabase.getInstance().getReference();

        ambilNama();
        ambilNoAdmin();

        getDetails();

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kegunaan1.setMaxLines(View.GONE);
                more.setVisibility(View.GONE);
            }
        });


        qty_stock1.setText("" +counter);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                qty_stock1.setText(""+counter);
            }
        });

        btn_kurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter--;
                qty_stock1.setText(""+counter);
            }
        });

        if (getIntent().hasExtra("imgurl") && getIntent().hasExtra("nama")){

            byte [] bytes = getIntent().getByteArrayExtra("imgurl");
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            String mNama = getIntent().getStringExtra("nama");
            String mHarga = getIntent().getStringExtra("harga");
            String mDesc = getIntent().getStringExtra("desc");

            iv_masker.setImageBitmap(bmp);
            nama_masker1.setText(mNama);
            harga_masker1.setText(mHarga);
            kegunaan1.setText(mDesc);

        }else {
            Toast.makeText(spesifikasi_masker.this, "Masker Belum memiliki gambar atau nama !", Toast.LENGTH_SHORT).show();
        }



        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int harga = Integer.parseInt(harga_masker1.getText().toString());
                int pesan = Integer.parseInt(qty_stock1.getText().toString());
                int total = harga * pesan;

                if (!isEmpty(nama_masker1.getText().toString()) && !isEmpty(kegunaan1.getText().toString()) && !isEmpty(harga_masker1.getText().toString()) && !isEmpty(qty_stock1.getText().toString())){
                    submitBarang(new Barang(
                            tv_user.getText().toString(),
                            nama_masker1.getText().toString(),
                            kegunaan1.getText().toString(),
                            String.valueOf(total),
                            qty_stock1.getText().toString(),
                            tv_alamat.getText().toString()
                    ));

                    String url = "https://api.whatsapp.com/send?phone="+noUser+"&text=Pesan Masker %0A %0ANama : "+tv_user.getText().toString()+"%0ANama Masker : "+
                            nama_masker1.getText().toString()+"%0APesan : "+qty_stock1.getText().toString()+"%0AAlamat : "+tv_alamat.getText().toString()+"%0AHarga : "+total;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                else{
                    Snackbar.make(findViewById(R.id.btnlogin), "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        nama_masker1.getWindowToken(), 0
                );
                }
            }
 });

    }

    private void getDetails(){
        if (kegunaan1.getMaxLines()>2){
            more.setVisibility(View.VISIBLE);
        }else {
            more.setVisibility(View.GONE);
        }
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }


    private void submitBarang(Barang barang) {
        database.child("barang").push().setValue(barang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(findViewById(R.id.btnlogin), "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private void ambilNama(){
        firebaseAuth = FirebaseAuth.getInstance();


        FirebaseUser usr = firebaseAuth.getCurrentUser();
        GetUserId = usr.getUid();

        getDatabase = FirebaseDatabase.getInstance();
        gwtReference = getDatabase.getReference();

        gwtReference.child("user").child(GetUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model_user user = dataSnapshot.getValue(model_user.class);
                String nama = user.getNama();
                tv_user.setText(nama);
                String alamat = user.getAlamat();
                tv_alamat.setText(alamat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void ambilNoAdmin(){
        String a = "admin";
        dbRef = FirebaseDatabase.getInstance().getReference("user");
        Query mQuery = dbRef.orderByChild("level").equalTo(a);
        mQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                model_user usr = dataSnapshot.getValue(model_user.class);
                noUser = usr.getNoHp();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                model_user usr = dataSnapshot.getValue(model_user.class);
                noUser = usr.getNoHp();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, spesifikasi_masker.class);

    }
}
