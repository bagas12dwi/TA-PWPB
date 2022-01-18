package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Halaman_Awal2 extends AppCompatActivity {

    private RecyclerView mRview;
    private KategoriAdapter mAdapter;
    private FloatingActionButton fablogout;

    private ProgressBar pgbar;

    private DatabaseReference mRef;
    private List<Kategori> mKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman__awal2);

        mRview = findViewById(R.id.recyler_view);
        mRview.setHasFixedSize(true);
        mRview.setLayoutManager(new LinearLayoutManager(this));
        pgbar = findViewById(R.id.progressBar);
        fablogout = findViewById(R.id.fablogout);

        fablogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Halaman_Awal2.this, Login.class));
                finish();
            }
        });

        mKategori = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("kategori");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Kategori ktgr = postSnapshot.getValue(Kategori.class);
                    mKategori.add(ktgr);
                }
                mAdapter = new KategoriAdapter(Halaman_Awal2.this, mKategori);
                mRview.setAdapter(mAdapter);
                pgbar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Halaman_Awal2.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                pgbar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
