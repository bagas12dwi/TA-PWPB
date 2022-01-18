package net.bag12.glowbeauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Daftar_user extends AppCompatActivity {

    private RecyclerView mRview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;

    private DatabaseReference mRef;
    private ArrayList<model_user> mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_user);
        mRview = findViewById(R.id.recyler_view);
        mRview.setHasFixedSize(true);
        mRview.setLayoutManager(new LinearLayoutManager(this));
        getData();

    }

    private void getData(){
        Toast.makeText(getApplicationContext(), "Mohon tunggu sebentar !", Toast.LENGTH_LONG).show();
        mUser = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("user");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        model_user modelUser = snapshot.getValue(model_user.class);
                        mUser.add(modelUser);
                    }
                    mAdapter = new userAdapter(mUser, Daftar_user.this);
                    mRview.setAdapter(mAdapter);

                    Toast.makeText(getApplicationContext(), "Data behasil dimuat", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "Data gagal dimuat !", Toast.LENGTH_LONG).show();
                    Log.e("Daftar_user", databaseError.getDetails()+" "+databaseError.getMessage());
                }
            });
    }
}
