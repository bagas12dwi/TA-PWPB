package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Manage_Kategori extends AppCompatActivity {

    private RecyclerView mRView;
    private Manage_KategoriAdapter madapter;

    private TextView tvJenis;
    private FloatingActionButton fabTambah;
    private ProgressBar progressBar;

    private DatabaseReference mRef;
    private List<Kategori> mKategoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__kategori);

        mRView = findViewById(R.id.recyler_view);
        mRView.setHasFixedSize(true);
        mRView.setLayoutManager(new LinearLayoutManager(this));
        tvJenis = findViewById(R.id.jnsMasker);
        fabTambah = findViewById(R.id.btnTambah);

        progressBar=findViewById(R.id.progressBar);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Manage_Kategori.this, upload_kategori.class));
                finish();
            }
        });

        mKategoris = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("kategori");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Kategori kategori = snapshot.getValue(Kategori.class);
                    mKategoris.add(kategori);
                }
                madapter = new Manage_KategoriAdapter(Manage_Kategori.this, mKategoris);
                mRView.setAdapter(madapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Manage_Kategori.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
