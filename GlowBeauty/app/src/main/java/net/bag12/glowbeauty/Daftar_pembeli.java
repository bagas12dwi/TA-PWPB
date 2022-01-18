package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Daftar_pembeli extends AppCompatActivity {

    private RecyclerView mRview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;

    private DatabaseReference mRef;
    private ArrayList<Barang> listBarang;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pembeli);
        mRview = findViewById(R.id.recyler_view);
        mRview.setHasFixedSize(true);
        mRview.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }

    private void getData(){
        Toast.makeText(getApplicationContext(), "Mohon tunggu sebentar !", Toast.LENGTH_LONG).show();
        listBarang = new  ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("barang");
                mRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Barang barang = snapshot.getValue(Barang.class);
                            listBarang.add(barang);
                        }
                        mAdapter = new pembeliAdapter(listBarang, Daftar_pembeli.this);
                        mRview.setAdapter(mAdapter);

                        Toast.makeText(getApplicationContext(), "Data Berhasil dimuat !", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Data Gagal dimuat !", Toast.LENGTH_LONG).show();
                        Log.e("Daftar_pembeli", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });
    }

}
