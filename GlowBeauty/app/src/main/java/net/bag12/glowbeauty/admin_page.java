package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class admin_page extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase getDatabase;
    private DatabaseReference database, gwtReference;
    private String GetUserId;
    private CardView cavMasker, cavPembeli, cavUser, cavKategori, cavAdmin;
    TextView tvAdmin;
    FloatingActionButton fabLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        tvAdmin = (TextView) findViewById(R.id.txadmin);
        fabLogout = (FloatingActionButton) findViewById(R.id.btnLogout);
        cavMasker = (CardView) findViewById(R.id.masker);
        cavPembeli = findViewById(R.id.cv_pembeli);
        cavUser = findViewById(R.id.cv_user);
        cavKategori = findViewById(R.id.kategori);
        cavAdmin = findViewById(R.id.cardAdmin);

        namaAdmin();

        fabLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(admin_page.this, Login.class));
                finish();
            }
        });

        cavMasker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_page.this, manageMasker.class));

            }
        });

        cavPembeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_page.this, Daftar_pembeli.class));

            }
        });

        cavUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_page.this, Daftar_user.class));

            }
        });

        cavKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_page.this, Manage_Kategori.class));

            }
        });

        cavAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(admin_page.this, Profil.class));

            }
        });



    }
    public void namaAdmin(){
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("user");


        FirebaseUser usr = firebaseAuth.getCurrentUser();
        GetUserId = usr.getUid();

        getDatabase = FirebaseDatabase.getInstance();
        gwtReference = getDatabase.getReference();

        gwtReference.child("user").child(GetUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                model_user user = dataSnapshot.getValue(model_user.class);
                String nama = user.getNama();
                tvAdmin.setText(nama);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
