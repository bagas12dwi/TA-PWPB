package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class manageMasker extends AppCompatActivity {

    private RecyclerView mRView;
    private manageMasker_adapter mAdapter;

    private TextView tvmerk, tvJenis;
    private FloatingActionButton fabTambah;
    private ProgressBar progressBar;

    private DatabaseReference mRef;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_masker);

        mRView = findViewById(R.id.recyler_view);
        mRView.setHasFixedSize(true);
        mRView.setLayoutManager(new LinearLayoutManager(this));
        tvJenis = findViewById(R.id.jnsMasker);
        fabTambah = findViewById(R.id.btnTambah);

        progressBar=findViewById(R.id.progressBar);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(manageMasker.this, upload_masker.class));
                finish();
            }
        });

        mUploads=new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("masker");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new manageMasker_adapter(manageMasker.this, mUploads);
                mRView.setAdapter(mAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(manageMasker.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }
}