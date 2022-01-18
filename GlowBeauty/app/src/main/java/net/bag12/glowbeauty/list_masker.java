package net.bag12.glowbeauty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list_masker extends AppCompatActivity {

    private RecyclerView mRView;
    private ImageAdapter mAdapter;

    private TextView tvmerk, tvJenis;
    private ProgressBar progressBar;
    private ImageView imgback;

    private DatabaseReference mRef;
    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_masker);

        tvmerk = findViewById(R.id.txMerk);
        mRView = findViewById(R.id.recyler_view);
        mRView.setHasFixedSize(true);
        mRView.setLayoutManager(new LinearLayoutManager(this));
        tvJenis = findViewById(R.id.jnsMasker);
        imgback = findViewById(R.id.imgback);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(list_masker.this, Halaman_Awal2.class));
                finish();
            }
        });

        progressBar=findViewById(R.id.progressBar);

        mUploads=new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("masker");

        Bundle bundle = getIntent().getExtras();
        String vMerk = bundle.getString("Merk");
        tvJenis.setText(vMerk);

        Query query = mRef.orderByChild("merk").equalTo(vMerk);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter(list_masker.this, mUploads);
                mRView.setAdapter(mAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(list_masker.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
